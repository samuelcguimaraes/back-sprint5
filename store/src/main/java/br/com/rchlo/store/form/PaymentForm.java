package br.com.rchlo.store.form;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.YearMonth;

public class PaymentForm {

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    @Size(max = 100)
    private String cardClientName;

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    private String cardNumber;

    @NotNull
    @Future
    private YearMonth cardExpiration;

    @NotBlank
    @Pattern(regexp = "\\d{3}")
    private String cardVerificationCode;

    public BigDecimal getValue() {
        return value;
    }

    public String getCardClientName() {
        return cardClientName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public YearMonth getCardExpiration() {
        return cardExpiration;
    }

    public String getCardVerificationCode() {
        return cardVerificationCode;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setCardClientName(String cardClientName) {
        this.cardClientName = cardClientName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpiration(YearMonth cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public void setCardVerificationCode(String cardVerificationCode) {
        this.cardVerificationCode = cardVerificationCode;
    }

    public Payment convert() {
        Card card = new Card(cardClientName, cardNumber, cardExpiration.toString(), cardVerificationCode);
        return new Payment(value, card);
    }
}
