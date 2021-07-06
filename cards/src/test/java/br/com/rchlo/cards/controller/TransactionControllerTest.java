package br.com.rchlo.cards.controller;

import br.com.rchlo.cards.builder.CardBuilder;
import br.com.rchlo.cards.builder.TransactionBuilder;
import br.com.rchlo.cards.domain.Transaction;
import br.com.rchlo.cards.repository.CardRepository;
import br.com.rchlo.cards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/schema.sql")
@ActiveProfiles("test")
class TransactionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Test
	void shouldResponseOkSearchExistingTransaction() throws Exception {
		
		final var transaction = this.buildTransaction();
		this.transactionRepository.save(transaction);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{uuid}", transaction.getUuid()))
		            .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void shouldResponseNotFoundSearchNotExistingTransaction() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{uuid}", UUID.randomUUID().toString()))
		            .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	private Transaction buildTransaction() {
		final var card = new CardBuilder().getDefaultInstance();
		this.cardRepository.save(card);
		
		return new TransactionBuilder().getTemplateDefaultWithOutCard().withCard(card).build();
	}
}