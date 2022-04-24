package com.paymentprovider.webfluxreactive.controllers;

import com.paymentprovider.webfluxreactive.controllers.dtos.PaymentRequestDto;
import com.paymentprovider.webfluxreactive.models.Payment;
import com.paymentprovider.webfluxreactive.publishers.PaymentPublisher;
import com.paymentprovider.webfluxreactive.repositories.InMemoryDatabaseProvider;
import com.paymentprovider.webfluxreactive.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentPublisher paymentPublisher;

    @PostMapping("create-payment")
    public Mono<Payment> createPayment(@RequestBody final PaymentRequestDto paymentRequestDto) {
        final String userId = paymentRequestDto.getUserId();

        log.info("Payment to be processing for userId {} ", userId);

        return this.paymentRepository.createPayment(userId, paymentRequestDto.getValor())
                .flatMap(payment -> paymentPublisher.onPaymentCreate(payment))
                /*
                .flatMap(payment -> {
                            Mono<Payment> paymentMono =
                                    Flux.interval(Duration.ofSeconds(1))
                                            .doOnNext(lg -> log.info("Next tkt {} ", lg))
                                    .flatMap(tkt -> this.paymentRepository.getPayment(userId))
                                    .filter(it -> StatusPayment.APPROVED == it.getStatus())
                                    .next();

                            return paymentMono;
                        })
                 */
                .doOnNext(next -> log.info("Payment Processed for user {}", userId))
                .timeout(Duration.ofSeconds(5))
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1))
                        .doAfterRetry(retrySignal -> log.info("Execution failed.... retrying {}", retrySignal)));
    }

    @GetMapping("users")
    public Flux<Payment> findAllById(@RequestParam("ids") String ids) {
        final List<String> listIds = Arrays.asList(ids.split(","));

        log.info("Collactions {} payments ", listIds.size());
        return Flux.fromIterable(listIds)
                .flatMap(id -> this.paymentRepository.getPayment(id), 5000)
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("ids")
    public Mono<String> findUserAll() {

        return Mono.fromCallable(() -> {
            return String.join(",", InMemoryDatabaseProvider.DATABASE.keySet());
        }).subscribeOn(Schedulers.parallel());
    }

}
