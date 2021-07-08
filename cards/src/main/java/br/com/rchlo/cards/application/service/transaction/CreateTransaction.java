package br.com.rchlo.cards.application.service.transaction;

import br.com.rchlo.cards.application.AvailableLimitExceededException;
import br.com.rchlo.cards.application.ResourceNotFoundException;
import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.application.service.fraud.FraudService;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.card.CardRepository;
import br.com.rchlo.cards.domain.transaction.Transaction;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

import java.util.Optional;

public class CreateTransaction {
	
	private final TransactionRequestDto transactionRequest;
	private final TransactionRepository transactionRepository;
	private final CardRepository cardRepository;
	private final FraudService fraudService;
	
	public CreateTransaction(final TransactionRequestDto transactionRequest,
	                         final TransactionRepository transactionRepository,
	                         final CardRepository cardRepository,
	                         final FraudService fraudService) {
		this.transactionRequest = transactionRequest;
		this.transactionRepository = transactionRepository;
		this.cardRepository = cardRepository;
		this.fraudService = fraudService;
	}
	
	public Transaction create() {
		
		final var card = this.validateCard();
		
		this.checkAvailableLimit(card);
		
		this.fraudService.checkFraud(this.transactionRequest, card);
		
		final Transaction transaction = this.transactionRequest.asEntity(card);
		this.transactionRepository.save(transaction);
		
		return transaction;
	}
	
	private Card validateCard() {
		
		final Optional<Card> possibleCard = this.cardRepository.findByHolderNameAndNumberAndExpirationAndSecurityCode(
				this.transactionRequest.getCardHolderName(),
				this.transactionRequest.getCardNumber(),
				this.transactionRequest.getCardExpiration().toString(),
				this.transactionRequest.getCardSecurityCode()
		);
		
		return possibleCard.orElseThrow(() -> new ResourceNotFoundException("Invalid card"));
	}
	
	private void checkAvailableLimit(final Card card) {
		if (card.hasNotAvailableLimit(this.transactionRequest.getAmount())) {
			throw new AvailableLimitExceededException("Limit not available");
		}
	}
}