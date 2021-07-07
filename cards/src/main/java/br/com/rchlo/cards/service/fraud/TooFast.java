package br.com.rchlo.cards.service.fraud;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.Transaction;
import br.com.rchlo.cards.dto.TransactionRequestDto;
import br.com.rchlo.cards.exception.FraudException;
import br.com.rchlo.cards.repository.TransactionRepository;

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
