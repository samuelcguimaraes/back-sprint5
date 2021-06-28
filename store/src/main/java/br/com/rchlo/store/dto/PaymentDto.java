package br.com.rchlo.store.dto;

import br.com.rchlo.store.domain.Payment;

import java.math.BigDecimal;

public class PaymentDto {

    private final Long id;
    private final BigDecimal value;
    private final String status;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.value = payment.getValue();
        this.status = payment.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }
}
