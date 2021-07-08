package br.com.rchlo.cards.application.service.fraud;

import br.com.rchlo.cards.application.dto.TransactionRequestDto;
import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

public interface Verifiable {
	
	void check(final TransactionRequestDto transactionRequest,
	           final Card card,
	           final TransactionRepository transactionRepository);
}
