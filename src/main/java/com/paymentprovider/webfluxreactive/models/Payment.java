package com.paymentprovider.webfluxreactive.models;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @With
public class Payment {
    private String paymentId;
    private BigDecimal valor;
    private LocalDateTime data;
    private String userId;
    private StatusPayment status;
}
