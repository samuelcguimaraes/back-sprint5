package br.com.rchlo.cards.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Card {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String holderName;
    private String number;
    private String expiration;
    private String securityCode;
    private String issuingCompany;

    private BigDecimal monthlyFee;

    private BigDecimal totalLimit;
    private BigDecimal availableLimit;

    @Embedded
    private Customer customer;

    /** @deprecated */
    protected Card() {
    }

    public Card(String holderName, String number, String expiration, String securityCode, String issuingCompany, BigDecimal monthlyFee, BigDecimal totalLimit, BigDecimal availableLimit, Customer customer) {
        this.holderName = holderName;
        this.number = number;
        this.expiration = expiration;
        this.securityCode = securityCode;
        this.issuingCompany = issuingCompany;
        this.monthlyFee = monthlyFee;
        this.totalLimit = totalLimit;
        this.availableLimit = availableLimit;
        this.customer = customer;
    }
    
    public BigDecimal getAvailableLimit() {
        return this.availableLimit;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void updateAvailableLimit(BigDecimal amount) {
        BigDecimal newCardLimit = this.getAvailableLimit().subtract(amount);
        this.availableLimit = newCardLimit;
    }
}
