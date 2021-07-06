package br.com.rchlo.cards.dto;

import br.com.rchlo.cards.domain.Card;
import br.com.rchlo.cards.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.YearMonth;

public class TransactionRequestDto {

    @NotNull
    @Positive
    @JsonProperty
    private BigDecimal amount;

    @NotBlank
    @JsonProperty
    private String description;

    @NotBlank
    @Size(max = 100)
    @JsonProperty
    private String cardHolderName;

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    @JsonProperty
    private String cardNumber;
    
    @NotNull
    @Future
    @JsonProperty
    private YearMonth cardExpiration;
    
    @NotBlank
    @Pattern(regexp = "\\d{3}")
    @JsonProperty
    private String cardSecurityCode;
    
    public TransactionRequestDto() {
    }
    
    public TransactionRequestDto(BigDecimal amount, String description) {
        this.amount = amount;
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return this.amount;
    }
    
    public String getCardHolderName() {
        return this.cardHolderName;
    }
    
    public String getCardNumber() {
        return this.cardNumber;
    }

    public YearMonth getCardExpiration() {
        return this.cardExpiration;
    }

    public String getCardSecurityCode() {
        return this.cardSecurityCode;
    }

    public Transaction asEntity(Card card) {
        return new Transaction(this.amount, this.description, card);
    }
}
