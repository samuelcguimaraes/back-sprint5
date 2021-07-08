package br.com.rchlo.cards.application.service.transaction;

import br.com.rchlo.cards.domain.transaction.Transaction;
import br.com.rchlo.cards.domain.transaction.TransactionRepository;

import java.util.Optional;

public class FindTransaction {
	
	private final TransactionRepository transactionRepository;
	
	public FindTransaction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public Optional<Transaction> findByUuid(final String uuid) {
		return this.transactionRepository.findByUuid(uuid);
	}
	
}