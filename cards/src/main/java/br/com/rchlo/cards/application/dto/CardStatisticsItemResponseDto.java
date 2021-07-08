package br.com.rchlo.cards.application.dto;

import java.util.DoubleSummaryStatistics;

public class CardStatisticsItemResponseDto {

    private final long count;
    private final double sum;
    private final double average;
    private final double max;
    private final double min;
    
    public CardStatisticsItemResponseDto(final DoubleSummaryStatistics transactionSummaryStatistics) {
        this.count = transactionSummaryStatistics.getCount();
        this.sum = transactionSummaryStatistics.getSum();
        this.average = transactionSummaryStatistics.getAverage();
        this.max = transactionSummaryStatistics.getMax();
        this.min = transactionSummaryStatistics.getMin();
    }

    public long getCount() {
        return this.count;
    }

    public double getSum() {
        return this.sum;
    }

    public double getAverage() {
        return this.average;
    }

    public double getMax() {
        return this.max;
    }

    public double getMin() {
        return this.min;
    }
}
