package br.com.rchlo.cards.controller;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.FraudVerifier;
import br.com.rchlo.cards.domain.Transaction;
import br.com.rchlo.cards.dto.TransactionRequestDto;
import br.com.rchlo.cards.dto.TransactionResponseDto;
import br.com.rchlo.cards.repository.CardRepository;
import br.com.rchlo.cards.repository.FraudVerifierRepository;
import br.com.rchlo.cards.repository.TransactionRepository;
import br.com.rchlo.cards.service.fraud.FraudService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TransactionController {
    
    private final EntityManager entityManager;
    private final Configuration freemarker;
    private final MailSender mailSender;
    
    private final CardRepository cardRepository;
    private final FraudVerifierRepository fraudVerifierRepository;
    private final TransactionRepository transactionRepository;
    
    public TransactionController(EntityManager entityManager, Configuration freemarker, MailSender mailSender,
                                 CardRepository cardRepository, FraudVerifierRepository fraudVerifierRepository,
                                 TransactionRepository transactionRepository) {
        this.entityManager = entityManager;
        this.freemarker = freemarker;
        this.mailSender = mailSender;
        this.cardRepository = cardRepository;
        this.fraudVerifierRepository = fraudVerifierRepository;
        this.transactionRepository = transactionRepository;
    }
    
    @GetMapping("/transactions/{uuid}")
    public ResponseEntity<TransactionResponseDto> detail(@PathVariable("uuid") String uuid) {
        Optional<Transaction> possibleTransaction = this.entityManager.createQuery(
                "select t from Transaction t where t.uuid = :uuid", Transaction.class)
                                                                            .setParameter("uuid", uuid)
                                                                            .getResultStream().findFirst();
        Transaction transaction = possibleTransaction.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(new TransactionResponseDto(transaction));
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDto> create(@RequestBody @Valid TransactionRequestDto transactionRequest, UriComponentsBuilder uriBuilder) {
    
        // inicio validacao se card existe
        Optional<Card> possibleCard = this.cardRepository.findByHolderNameAndNumberAndExpirationAndSecurityCode(
                transactionRequest.getCardHolderName(),
                transactionRequest.getCardNumber(),
                transactionRequest.getCardExpiration().toString(),
                transactionRequest.getCardSecurityCode()
        );
        Card card = possibleCard.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card"));
        // fim validacao se card existe
    
        // inicio verificacao limite disponivel
        if (card.hasNotAvailableLimit(transactionRequest.getAmount())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit not available");
        }
        // fim verificacao limite disponivel
    
        // inicio verificacao de fraude
        List<FraudVerifier> enabledFraudVerifiers = this.fraudVerifierRepository.findByEnabledTrue();
    
        FraudService fraudService = new FraudService(enabledFraudVerifiers, transactionRequest, card,
                                                           this.transactionRepository);
    
        if (!fraudService.checkFraud()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fraud detected");
        }
        // fim verificacao de fraude
    
        // salva transacao
        Transaction transaction = transactionRequest.asEntity(card);
        this.transactionRepository.save(transaction);
    
        URI uri = uriBuilder.path("/transactions/{uuid}").buildAndExpand(transaction.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new TransactionResponseDto(transaction));
    }

    @Transactional
    @PutMapping("/transactions/{uuid}")
    public ResponseEntity<Void> confirm(@PathVariable("uuid") String uuid) {
        Optional<Transaction> possibleTransaction = this.entityManager.createQuery("select t from Transaction t where t.uuid = :uuid", Transaction.class)
                                                                            .setParameter("uuid", uuid)
                                                                            .getResultStream().findFirst();
        Transaction transaction = possibleTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!transaction.confirm()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction status");
        }

        // atualiza limite do cartao
        Card card = transaction.getCard();
        card.updateAvailableLimit(transaction.getAmount());

        // inicio criacao texto de notificacao
        String notificationText = "";
        try {
            Template template = this.freemarker.getTemplate("expense-notification.ftl");
            Map<String, Object> data = new HashMap<>();
            data.put("transaction", transaction);
            StringWriter out = new StringWriter();
            template.process(data, out);
            notificationText = out.toString();
        } catch (IOException | TemplateException ex) {
            throw new IllegalStateException(ex);
        }
        // fim criacao texto de notificacao

        // inicio envio notificacao por email
        var message = new SimpleMailMessage();
        message.setFrom("noreply@rchlo.com.br");
        message.setTo(card.getCustomer().getEmail());
        message.setSubject("Nova despesa: " + transaction.getDescription());
        message.setText(notificationText);
        this.mailSender.send(message);   // para verificar o email enviado acesse: https://www.smtpbucket.com/emails.
                                    // Coloque noreply@rchlo.com.br em Sender e o email do cliente no Recipient.
        // fim envio notificacao por email

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/transactions/{uuid}")
    public ResponseEntity<Void> cancel(@PathVariable("uuid") String uuid) {
        Optional<Transaction> possibleTransaction = this.entityManager.createQuery(
                "select t from Transaction t where t.uuid = :uuid", Transaction.class)
                                                                            .setParameter("uuid", uuid)
                                                                            .getResultStream().findFirst();
        Transaction transaction = possibleTransaction.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
        if (!transaction.cancel()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction status");
        }
    
        return ResponseEntity.ok().build();
    }
}
