package br.com.rchlo.cards.application.service.fraud;

import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.fraud.FraudException;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

public class ExpendsAllLimit implements Verifiable {
	
	@Override
	public void check(final TransactionRequestDto transactionRequest,
	                  final Card card,
	                  final TransactionRepository transactionRepository) throws FraudException {
		
		if (transactionRequest.getAmount().compareTo(card.getAvailableLimit()) == 0) {
			throw new FraudException("Fraud detected: Expends all limit");
		}
	}
}
