package br.com.rchlo.cards.application.dto;

import br.com.rchlo.cards.domain.transaction.Transaction;

import java.math.BigDecimal;

public class TransactionResponseDto {
    
    private final String uuid;
    private final String status;
    private final String description;
    private final BigDecimal amount;
    
    public TransactionResponseDto(final Transaction transaction) {
        this.uuid = transaction.getUuid();
        this.status = transaction.getStatus().name();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
    }
    
    public String getUuid() {
        return this.uuid;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public BigDecimal getAmount() {
        return this.amount;
    }
}
