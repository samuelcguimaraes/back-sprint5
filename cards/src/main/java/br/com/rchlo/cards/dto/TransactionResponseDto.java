package br.com.rchlo.cards.dto;

import br.com.rchlo.cards.domain.Transaction;

import java.math.BigDecimal;

public class TransactionResponseDto {

    private final String uuid;
    private final String status;
    private final String description;
    private final BigDecimal amount;

    public TransactionResponseDto(Transaction transaction) {
        this.uuid = transaction.getUuid();
        this.status = transaction.getStatus().name();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
    }

    public String getUuid() {
        return uuid;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
