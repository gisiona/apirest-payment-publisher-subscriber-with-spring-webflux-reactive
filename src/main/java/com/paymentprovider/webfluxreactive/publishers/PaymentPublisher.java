package com.paymentprovider.webfluxreactive.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentprovider.webfluxreactive.models.Payment;
import com.paymentprovider.webfluxreactive.models.PubSubMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {
    private final Sinks.Many<PubSubMessage> sink;
    private final ObjectMapper mapper;

    public Mono<Payment> onPaymentCreate(final  Payment payment) {
        return Mono.fromCallable(() -> {
            final String userId = payment.getUserId();
            final String dataJson = mapper.writeValueAsString(payment);
            return new PubSubMessage(userId, dataJson);
        }).subscribeOn(Schedulers.parallel())
                .doOnNext(next -> this.sink.tryEmitNext(next))
                .thenReturn(payment);
    }
}
