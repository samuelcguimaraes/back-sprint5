package br.com.rchlo.cards.builder;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.Customer;

import java.math.BigDecimal;

public class CardBuilder {
	
	private String holderName;
	private String number;
	private String expiration;
	private String securityCode;
	private String issuingCompany;
	private BigDecimal monthlyFee;
	private BigDecimal totalLimit;
	private BigDecimal availableLimit;
	private Customer customer;
	
	public CardBuilder withHolderName(final String holderName) {
		this.holderName = holderName;
		return this;
	}
	
	public CardBuilder withNumber(final String number) {
		this.number = number;
		return this;
	}
	
	public CardBuilder withExpiration(final String expiration) {
		this.expiration = expiration;
		return this;
	}
	
	public CardBuilder withSecurityCode(final String securityCode) {
		this.securityCode = securityCode;
		return this;
	}
	
	public CardBuilder withIssuingCompany(final String issuingCompany) {
		this.issuingCompany = issuingCompany;
		return this;
	}
	
	public CardBuilder withMonthlyFee(final BigDecimal monthlyFee) {
		this.monthlyFee = monthlyFee;
		return this;
	}
	
	public CardBuilder withTotalLimit(final BigDecimal totalLimit) {
		this.totalLimit = totalLimit;
		return this;
	}
	
	public CardBuilder withAvailableLimit(final BigDecimal availableLimit) {
		this.availableLimit = availableLimit;
		return this;
	}
	
	public CardBuilder withCustomer(final Customer customer) {
		this.customer = customer;
		return this;
	}
	
	public Card build() {
		return new Card(this.holderName, this.number, this.expiration, this.securityCode, this.issuingCompany,
		                this.monthlyFee, this.totalLimit,
		                this.availableLimit, this.customer);
	}
	
	public Card getDefaultInstance() {
		return new CardBuilder().withHolderName("Holder Name")
		                        .withNumber("1111222233334444")
		                        .withExpiration("999912")
		                        .withSecurityCode("123")
		                        .withIssuingCompany("Issuing Company")
		                        .withMonthlyFee(BigDecimal.ZERO)
		                        .withTotalLimit(BigDecimal.TEN)
		                        .withAvailableLimit(BigDecimal.ONE)
		                        .withCustomer(new CustomerBuilder().getDefaultInstance())
		                        .build();
	}
}
