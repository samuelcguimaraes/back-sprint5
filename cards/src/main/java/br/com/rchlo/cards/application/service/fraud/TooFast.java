package br.com.rchlo.cards.application.service.fraud;

import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.fraud.FraudException;
import br.com.rchlo.cards.domain.transaction.Transaction;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TooFast implements Verifiable {
	
	@Override
	public void check(final TransactionRequestDto transactionRequest,
	                  final Card card,
	                  final TransactionRepository transactionRepository) throws FraudException {
		
		final LocalDateTime timeOfLastConfirmedTransactionForCard;
		
		timeOfLastConfirmedTransactionForCard =
				transactionRepository.findMaxCreatedAt(card, Transaction.Status.CONFIRMED);
		
		if (timeOfLastConfirmedTransactionForCard != null) {
			final long secondsFromLastConfirmedTransactionForCard =
					ChronoUnit.SECONDS.between(timeOfLastConfirmedTransactionForCard, LocalDateTime.now());
			if (secondsFromLastConfirmedTransactionForCard < 30) {
				throw new FraudException("Fraud detected: Too fast");
			}
		}
	}
}
