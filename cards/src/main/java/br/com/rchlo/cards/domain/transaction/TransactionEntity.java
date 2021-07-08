package br.com.rchlo.cards.domain.transaction;

import br.com.rchlo.cards.domain.card.Card;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionEntity {
	
	private Long id;
	private String uuid;
	private BigDecimal amount;
	private String description;
	private Transaction.Status status;
	private LocalDateTime createdAt;
	private Card card;
	
}
