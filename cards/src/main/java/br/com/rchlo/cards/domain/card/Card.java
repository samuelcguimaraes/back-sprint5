package br.com.rchlo.cards.domain.card;

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

    public Card(final String holderName, final String number, final String expiration, final String securityCode, final String issuingCompany, final BigDecimal monthlyFee, final BigDecimal totalLimit, final BigDecimal availableLimit, final Customer customer) {
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
    
    public void updateAvailableLimit(final BigDecimal amount) {
        this.availableLimit = this.getAvailableLimit().subtract(amount);
    }
    
    public boolean hasNotAvailableLimit(final BigDecimal amount) {
        return amount.compareTo(this.availableLimit) > 0;
    }
}
