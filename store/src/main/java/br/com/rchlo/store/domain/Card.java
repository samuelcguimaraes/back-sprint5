package br.com.rchlo.store.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Card {

    private String clientName;
    private String number;
    private String expiration;
    private String verificationCode;

    /** @deprecated */
    protected Card() {
    }

    public Card(String clientName, String number, String expiration, String verificationCode) {
        this.clientName = clientName;
        this.number = number;
        this.expiration = expiration;
        this.verificationCode = verificationCode;
    }
}
