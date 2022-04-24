package com.paymentprovider.webfluxreactive.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @With
public class Payment {
    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("data")
    private LocalDateTime data;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("status_payment")
    private StatusPayment status;
}
