package br.com.rchlo.store.controller;

import br.com.rchlo.store.builder.PaymentBuilder;
import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.YearMonth;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/schema.sql")
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void shouldNotConfirmAnAlreadyCanceledPayment() throws Exception {
        Payment canceledPayment = aPayment().canceled().build();
        paymentRepository.save(canceledPayment);

        mockMvc.perform(MockMvcRequestBuilders.put("/payments/{id}", canceledPayment.getId()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldNotCancelAnAlreadyConfirmedPayment() throws Exception {
        Payment canceledPayment = aPayment().confirmed().build();
        paymentRepository.save(canceledPayment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/payments/{id}", canceledPayment.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private PaymentBuilder aPayment() {
        return new PaymentBuilder().of(new BigDecimal("49.90")).fromClient("ANDERSON SILVA")
                .withCardNumber("1111222233334444").withExpiration(YearMonth.of(2032, 1))
                .withVerificationCode("123");
    }

}