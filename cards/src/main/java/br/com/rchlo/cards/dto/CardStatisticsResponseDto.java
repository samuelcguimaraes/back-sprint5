package br.com.rchlo.cards.dto;

import br.com.rchlo.cards.domain.Transaction;

import java.time.YearMonth;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.TreeMap;

public class CardStatisticsResponseDto {
    
    private final TreeMap<YearMonth, CardStatisticsItemResponseDto> cardStatistics = new TreeMap<>();
    
    public void addItem(YearMonth yearMonth, List<Transaction> transactionsForYearMonth) {
        DoubleSummaryStatistics transactionSummaryStatistics = transactionsForYearMonth.stream().mapToDouble(
                transaction -> transaction.getAmount().doubleValue()).summaryStatistics();
        this.cardStatistics.put(yearMonth, new CardStatisticsItemResponseDto(transactionSummaryStatistics));
    }
    
    public TreeMap<YearMonth, CardStatisticsItemResponseDto> getCardStatistics() {
        return this.cardStatistics;
    }
}
