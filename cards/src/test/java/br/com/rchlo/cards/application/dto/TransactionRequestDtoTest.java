package br.com.rchlo.cards.application.dto;

import br.com.rchlo.cards.builder.CardBuilder;
import br.com.rchlo.cards.domain.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.rchlo.cards.domain.transaction.Transaction.Status.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRequestDtoTest {
	
	public static final String DESCRIPTION = "Descrição";
	public static final BigDecimal AMOUNT_TRANSACTION = BigDecimal.TEN;
	
	private TransactionRequestDto transactionRequestDto;
	private Card card;
	
	@BeforeEach
	void setUp() {
		this.card = new CardBuilder().getDefaultInstance();
		
		this.transactionRequestDto = new TransactionRequestDto(AMOUNT_TRANSACTION, DESCRIPTION);
	}
	
	@Test
	void shouldConvertTransactionRequestDtoToTransaction() {
		final var transaction = this.transactionRequestDto.asEntity(this.card);
		
		assertEquals(DESCRIPTION, transaction.getDescription());
		assertEquals(AMOUNT_TRANSACTION, transaction.getAmount());
		assertEquals(CREATED, transaction.getStatus());
		assertEquals(LocalDate.now(), transaction.getCreatedAt().toLocalDate());
		assertEquals(this.card, transaction.getCard());
	}
}