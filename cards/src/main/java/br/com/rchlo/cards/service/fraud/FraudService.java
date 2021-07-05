package br.com.rchlo.cards.service.fraud;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.FraudVerifier;
import br.com.rchlo.cards.domain.Transaction;
import br.com.rchlo.cards.dto.TransactionRequestDto;
import br.com.rchlo.cards.repository.TransactionRepository;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FraudService {
	
	private final List<FraudVerifier> enabledFraudVerifiers;
	private final TransactionRequestDto transactionRequest;
	private final Card card;
	private final TransactionRepository transactionRepository;
	
	public FraudService(List<FraudVerifier> enabledFraudVerifiers,
	                    TransactionRequestDto transactionRequest,
	                    Card card,
	                    TransactionRepository transactionRepository) {
		this.enabledFraudVerifiers = enabledFraudVerifiers;
		this.transactionRequest = transactionRequest;
		this.card = card;
		this.transactionRepository = transactionRepository;
	}
	
	public boolean checkFraud() {
		
		// fraude: gastar o limite de uma vez
		if (this.enabledFraudVerifiers.stream().map(FraudVerifier::getType)
		                              .anyMatch(FraudVerifier.Type.EXPENDS_ALL_LIMIT::equals)) {
			if (this.transactionRequest.getAmount().compareTo(this.card.getAvailableLimit()) == 0) {
				return false;
			}
		}
		
		// fraude: duas transacoes com menos de 30 segundos
		if (this.enabledFraudVerifiers.stream().map(FraudVerifier::getType)
		                              .anyMatch(FraudVerifier.Type.TOO_FAST::equals)) {
			LocalDateTime timeOfLastConfirmedTransactionForCard = null;
			try {
				timeOfLastConfirmedTransactionForCard = this.transactionRepository.findMaxCreatedAt(this.card,
				                                                                                    Transaction.Status.CONFIRMED);
			} catch (NoResultException ex) {
			}
			if (timeOfLastConfirmedTransactionForCard != null) {
				long secondsFromLastConfirmedTransactionForCard = ChronoUnit.SECONDS.between(
						timeOfLastConfirmedTransactionForCard, LocalDateTime.now());
				if (secondsFromLastConfirmedTransactionForCard < 30) {
					return false;
				}
			}
		}
		
		return true;
	}
}
