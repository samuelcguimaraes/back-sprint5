package br.com.rchlo.cards.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.rchlo.cards.domain.Transaction.Status.*;

@Entity
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private BigDecimal amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;

    @ManyToOne
    private Card card;

    /** @deprecated */
    protected Transaction() {
    }

    public Transaction(BigDecimal amount, String description, Card card) {
        this.amount = amount;
        this.description = description;
        this.card = card;
        this.uuid = UUID.randomUUID().toString();
        this.status = CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public String getUuid() {
        return this.uuid;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
    
    public Card getCard() {
        return this.card;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public boolean isCreated() {
        return CREATED.equals(this.status);
    }
    
    public boolean confirm() {
        
        if (!this.isCreated()) {
            return false;
        }
        
        this.status = CONFIRMED;
        
        return true;
    }
    
    public boolean cancel() {
        
        if (!this.isCreated()) {
            return false;
        }
        
        this.status = CANCELED;
        
        return true;
    }
    
    public enum Status {
        
        CREATED,
        CONFIRMED,
        CANCELED
        
    }
}
