package br.com.rchlo.cards.builder;

import br.com.rchlo.cards.domain.card.Card;
import br.com.rchlo.cards.domain.transaction.Transaction;

import java.math.BigDecimal;

public class TransactionBuilder {
	
	private BigDecimal amount;
	private String description;
	private Card card;
	
	public TransactionBuilder withAmount(final BigDecimal amount) {
		this.amount = amount;
		return this;
	}
	
	public TransactionBuilder withDescription(final String description) {
		this.description = description;
		return this;
	}
	
	public TransactionBuilder getTemplateDefaultWithOutCard() {
		this.amount = BigDecimal.TEN;
		this.description = "Descrição";
		return this;
	}
	
	public TransactionBuilder withCard(final Card card) {
		this.card = card;
		return this;
	}
	
	public Transaction build() {
		return new Transaction(this.amount, this.description, this.card);
	}
	
}
