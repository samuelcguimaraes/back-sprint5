package br.com.rchlo.cards.dto;

import br.com.rchlo.cards.builder.CardBuilder;
import br.com.rchlo.cards.builder.TransactionBuilder;
import br.com.rchlo.cards.domain.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Collections;

class CardStatisticsResponseDtoTest {
	
	private Card card;
	
	private CardStatisticsResponseDto cardStatisticsResponseDto;
	
	@BeforeEach
	void setUp() {
		this.cardStatisticsResponseDto = new CardStatisticsResponseDto();
		this.card = new CardBuilder().getDefaultInstance();
	}
	
	@Test
	void shouldNotHaveStatistics() {
		
		final var cardStatistics = this.cardStatisticsResponseDto.getCardStatistics();
		
		Assertions.assertTrue(cardStatistics.isEmpty());
	}
	
	@Test
	void shouldHaveStatistics() {
		
		final var transaction = new TransactionBuilder()
				                        .getTemplateDefaultWithOutCard()
				                        .withCard(this.card)
				                        .build();
		
		this.cardStatisticsResponseDto.addItem(YearMonth.now(), Collections.singletonList(transaction));
		
		final var cardStatistics = this.cardStatisticsResponseDto.getCardStatistics();
		
		Assertions.assertEquals(1, cardStatistics.size());
		Assertions.assertTrue(cardStatistics.containsKey(YearMonth.now()));
		Assertions.assertEquals(1, cardStatistics.get(YearMonth.now()).getCount());
		Assertions.assertEquals(BigDecimal.TEN.doubleValue(), cardStatistics.get(YearMonth.now()).getSum());
		Assertions.assertEquals(BigDecimal.TEN.doubleValue(), cardStatistics.get(YearMonth.now()).getAverage());
		Assertions.assertEquals(BigDecimal.TEN.doubleValue(), cardStatistics.get(YearMonth.now()).getMax());
		Assertions.assertEquals(BigDecimal.TEN.doubleValue(), cardStatistics.get(YearMonth.now()).getMin());
	}
}