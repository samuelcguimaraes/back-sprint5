package br.com.rchlo.cards.adapter.repository;

import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.transaction.Transaction;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long>, TransactionRepository {
	
	@Query("select max(t.createdAt) from Transaction t where t.card = :card and t.status = :status")
	LocalDateTime findMaxCreatedAt(Card card, Transaction.Status status);
	
	Optional<Transaction> findByUuid(String uuid);
	
	@Override
	Transaction save(Transaction entity);
}
