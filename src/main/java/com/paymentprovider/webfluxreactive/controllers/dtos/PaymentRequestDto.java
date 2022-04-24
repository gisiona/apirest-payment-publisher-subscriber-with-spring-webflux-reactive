package com.paymentprovider.webfluxreactive.controllers.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {
    private String userId;
    private BigDecimal valor;
}
