package br.com.rchlo.cards.repository;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select max(t.createdAt) from Transaction t where t.card = :card and t.status = :status")
	LocalDateTime findMaxCreatedAt(Card card, Transaction.Status status);
}
