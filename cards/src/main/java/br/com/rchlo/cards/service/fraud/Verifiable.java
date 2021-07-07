package br.com.rchlo.cards.service.fraud;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.dto.TransactionRequestDto;
import br.com.rchlo.cards.exception.FraudException;
import br.com.rchlo.cards.repository.TransactionRepository;

public interface Verifiable {
	
	void check(final TransactionRequestDto transactionRequest,
	           final Card card,
	           final TransactionRepository transactionRepository) throws FraudException;
}
