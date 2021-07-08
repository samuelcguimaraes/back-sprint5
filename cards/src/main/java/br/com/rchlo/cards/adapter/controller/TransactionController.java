package br.com.rchlo.cards.adapter.controller;

import br.com.rchlo.cards.adapter.FraudServiceSpring;
import br.com.rchlo.cards.adapter.SendServiceSpring;
import br.com.rchlo.cards.adapter.TextServiceFreemarker;
import br.com.rchlo.cards.adapter.repository.CardJpaRepository;
import br.com.rchlo.cards.adapter.repository.TransactionJpaRepository;
import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.application.dto.TransactionResponseDto;
import br.com.rchlo.cards.application.service.fraud.FraudService;
import br.com.rchlo.cards.application.service.notification.SendService;
import br.com.rchlo.cards.application.service.notification.TextService;
import br.com.rchlo.cards.application.service.transaction.CreateTransaction;
import br.com.rchlo.cards.application.service.transaction.FindTransaction;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.card.CardRepository;
import br.com.rchlo.cards.domain.transaction.Transaction;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class TransactionController {
    
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    
    private final FraudService fraudService;
    private final TextService textService;
    private final SendService sendService;
    
    public TransactionController(final CardJpaRepository cardRepository,
                                 final FraudServiceSpring fraudService,
                                 final TextServiceFreemarker textService,
                                 final SendServiceSpring sendService,
                                 final TransactionJpaRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.fraudService = fraudService;
        this.textService = textService;
        this.sendService = sendService;
        this.transactionRepository = transactionRepository;
    }
    
    @GetMapping("/transactions/{uuid}")
    public ResponseEntity<TransactionResponseDto> detail(@PathVariable("uuid") final String uuid) {
        
        final Optional<Transaction> possibleTransaction =
                new FindTransaction(this.transactionRepository).findByUuid(uuid);
        
        final Transaction transaction =
                possibleTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        return ResponseEntity.ok().body(new TransactionResponseDto(transaction));
    }
    
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDto> create(
            @RequestBody @Valid final TransactionRequestDto transactionRequest, final UriComponentsBuilder uriBuilder) {
    
        final CreateTransaction createTransaction = new CreateTransaction(transactionRequest,
                                                                          this.transactionRepository,
                                                                          this.cardRepository,
                                                                          this.fraudService);
    
        final Transaction transaction;
    
        try {
            transaction = createTransaction.create();
        } catch (final RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    
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
