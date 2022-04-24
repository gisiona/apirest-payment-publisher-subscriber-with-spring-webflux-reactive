package com.paymentprovider.webfluxreactive.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("valor")
    private BigDecimal valor;
}
