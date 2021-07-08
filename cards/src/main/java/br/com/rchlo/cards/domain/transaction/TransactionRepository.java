package br.com.rchlo.cards.domain.transaction;

import br.com.rchlo.cards.domain.card.Card;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository {
	
	LocalDateTime findMaxCreatedAt(Card card, Transaction.Status status);
	
	Optional<Transaction> findByUuid(String uuid);
	
	Transaction save(Transaction transaction);
}
