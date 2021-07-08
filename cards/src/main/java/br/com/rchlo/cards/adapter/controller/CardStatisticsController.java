package br.com.rchlo.cards.adapter.controller;

import br.com.rchlo.cards.application.dto.CardStatisticsResponseDto;
import br.com.rchlo.cards.domain.transaction.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CardStatisticsController {
    
    private final EntityManager entityManager;
    
    public CardStatisticsController(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @GetMapping("/admin/cards/statistics/{number}")
    public ResponseEntity<CardStatisticsResponseDto> calculateStatistics(
            @PathVariable("number") final String cardNumber) {
        // inicio busca transacoes do card
        final List<Transaction> allCardTransactions = this.entityManager.createQuery(
                "select t from Transaction t where t.card.number = :number ", Transaction.class)
                                                                        .setParameter("number", cardNumber)
                                                                        .getResultList();
        // fim busca transacoes do card
        
        if (allCardTransactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        // inicio calculo estatisticas das transacoes do cartao
        final var cardStatistics = new CardStatisticsResponseDto();
        final Map<YearMonth, List<Transaction>> transactionsPerYearMonth =
                allCardTransactions.stream().collect(
                        Collectors.groupingBy(transaction -> YearMonth.from(transaction.getCreatedAt())));
        for (final YearMonth yearMonth : transactionsPerYearMonth.keySet()) {
            final List<Transaction> transactionsForYearMonth = transactionsPerYearMonth.get(yearMonth);
            cardStatistics.addItem(yearMonth, transactionsForYearMonth);
        }
        // fim calculo estatisticas das transacoes do cartao
        return ResponseEntity.ok().body(cardStatistics);
    }


}
