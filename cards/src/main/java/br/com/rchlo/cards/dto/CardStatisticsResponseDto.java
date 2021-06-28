package br.com.rchlo.cards.dto;

import br.com.rchlo.cards.domain.Transaction;

import java.time.YearMonth;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CardStatisticsResponseDto extends TreeMap<YearMonth, CardStatisticsItemResponseDto> {

    public void addItem(YearMonth yearMonth, List<Transaction> transactionsForYearMonth) {
        DoubleSummaryStatistics transactionSummaryStatistics = transactionsForYearMonth.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).summaryStatistics();
        this.put(yearMonth, new CardStatisticsItemResponseDto(transactionSummaryStatistics));
    }

    @Override
    public CardStatisticsItemResponseDto remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends YearMonth, ? extends CardStatisticsItemResponseDto> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

}
