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
import br.com.rchlo.cards.service.notification.SendService;
import br.com.rchlo.cards.service.notification.TextService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {
    
    private final CardRepository cardRepository;
    private final FraudVerifierRepository fraudVerifierRepository;
    private final TransactionRepository transactionRepository;
    
    private final FraudService fraudService;
    private final TextService textService;
    private final SendService sendService;
    
    public TransactionController(final CardRepository cardRepository,
                                 final FraudVerifierRepository fraudVerifierRepository,
                                 final TransactionRepository transactionRepository, final FraudService fraudService,
                                 final TextService textService, final SendService sendService) {
        this.cardRepository = cardRepository;
        this.fraudVerifierRepository = fraudVerifierRepository;
        this.transactionRepository = transactionRepository;
        this.fraudService = fraudService;
        this.textService = textService;
        this.sendService = sendService;
    }
    
    @GetMapping("/transactions/{uuid}")
    public ResponseEntity<TransactionResponseDto> detail(@PathVariable("uuid") final String uuid) {
        final Optional<Transaction> possibleTransaction = this.transactionRepository.findByUuid(uuid);
        
        final Transaction transaction = possibleTransaction.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(new TransactionResponseDto(transaction));
    }
    
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDto> create(
            @RequestBody @Valid final TransactionRequestDto transactionRequest, final UriComponentsBuilder uriBuilder) {
        
        // inicio validacao se card existe
        final Optional<Card> possibleCard = this.cardRepository.findByHolderNameAndNumberAndExpirationAndSecurityCode(
                transactionRequest.getCardHolderName(),
                transactionRequest.getCardNumber(),
                transactionRequest.getCardExpiration().toString(),
                transactionRequest.getCardSecurityCode()
        );
        final Card card = possibleCard.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card"));
        // fim validacao se card existe
        
        // inicio verificacao limite disponivel
        if (card.hasNotAvailableLimit(transactionRequest.getAmount())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Limit not available");
        }
        // fim verificacao limite disponivel
        
        // inicio verificacao de fraude
        final List<FraudVerifier> enabledFraudVerifiers = this.fraudVerifierRepository.findByEnabledTrue();
        if (!this.fraudService.checkFraud(enabledFraudVerifiers, transactionRequest, card)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fraud detected");
        }
        // fim verificacao de fraude
        
        // salva transacao
        final Transaction transaction = transactionRequest.asEntity(card);
        this.transactionRepository.save(transaction);
        
        final URI uri = uriBuilder.path("/transactions/{uuid}").buildAndExpand(transaction.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new TransactionResponseDto(transaction));
    }
    
    @Transactional
    @PutMapping("/transactions/{uuid}")
    public ResponseEntity<Void> confirm(@PathVariable("uuid") final String uuid) {
        final Optional<Transaction> possibleTransaction = this.transactionRepository.findByUuid(uuid);
        
        final Transaction transaction = possibleTransaction.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if (!transaction.confirm()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction status");
        }
        
        // atualiza limite do cartao
        final Card card = transaction.getCard();
        card.updateAvailableLimit(transaction.getAmount());
        
        // inicio criacao texto de notificacao
        final String notificationText = this.textService.generateTextNotification(transaction);
        // fim criacao texto de notificacao
    
        // inicio envio notificacao por email
        this.sendService.sendMessage(card.getCustomer().getEmail(), transaction.getDescription(), notificationText);
        // fim envio notificacao por email
    
        return ResponseEntity.ok().build();
    }
    
    @Transactional
    @DeleteMapping("/transactions/{uuid}")
    public ResponseEntity<Void> cancel(@PathVariable("uuid") final String uuid) {
        final Optional<Transaction> possibleTransaction = this.transactionRepository.findByUuid(uuid);
        
        final Transaction transaction = possibleTransaction.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if (!transaction.cancel()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction status");
        }
        
        return ResponseEntity.ok().build();
    }
}
