package br.com.rchlo.cards.builder;

import br.com.rchlo.cards.domain.Customer;

public class CustomerBuilder {
	
	private String fullName;
	private String address;
	private String email;
	
	public CustomerBuilder withFullName(final String fullName) {
		this.fullName = fullName;
		return this;
	}
	
	public CustomerBuilder withAddress(final String address) {
		this.address = address;
		return this;
	}
	
	public CustomerBuilder withEmail(final String email) {
		this.email = email;
		return this;
	}
	
	public Customer build() {
		return new Customer(this.fullName, this.address, this.email);
	}
	
	public Customer getDefaultInstance() {
		return new CustomerBuilder().withFullName("Full Name")
		                            .withAddress("Address")
		                            .withEmail("email@email.com")
		                            .build();
	}
}
