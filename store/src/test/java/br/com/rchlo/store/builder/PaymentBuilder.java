package br.com.rchlo.store.builder;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.YearMonth;

public class PaymentBuilder {

    private BigDecimal value;
    private PaymentStatus status;
    private String clientName;
    private String number;
    private YearMonth expiration;
    private String verificationCode;

    public PaymentBuilder of(BigDecimal value) {
        this.value = value;
        return this;
    }

    public PaymentBuilder fromClient(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public PaymentBuilder withCardNumber(String number) {
        this.number = number;
        return this;
    }

    public PaymentBuilder withExpiration(YearMonth expiration) {
        this.expiration = expiration;
        return this;
    }

    public PaymentBuilder withVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public PaymentBuilder confirmed() {
        this.status = PaymentStatus.CONFIRMED;
        return this;
    }

    public PaymentBuilder canceled() {
        this.status = PaymentStatus.CANCELED;
        return this;
    }

    public Payment build () {
        Card card = new Card(clientName, number, expiration.toString(), verificationCode);
        Payment payment = new Payment(value, card);
        if (PaymentStatus.CONFIRMED.equals(status)) {
            payment.confirm();
        } else if (PaymentStatus.CANCELED.equals(status)) {
            payment.cancel();
        }
        return payment;
    }

}
