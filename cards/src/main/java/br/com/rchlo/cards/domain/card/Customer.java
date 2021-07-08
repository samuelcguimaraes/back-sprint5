package br.com.rchlo.cards.domain.card;

import javax.persistence.Embeddable;

@Embeddable
public class Customer {

    private String fullName;
    private String address;
    private String email;

    /** @deprecated  */
    protected Customer() { }

    public Customer(final String fullName, final String address, final String email) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

}
