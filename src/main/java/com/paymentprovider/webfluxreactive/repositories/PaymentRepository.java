package com.paymentprovider.webfluxreactive.repositories;

import com.paymentprovider.webfluxreactive.models.Payment;
import com.paymentprovider.webfluxreactive.models.StatusPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRepository {
    private final Database database;

    private static final ThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("database-");

    private static final Scheduler SCHEDULER_DB = Schedulers.fromExecutor(Executors.newFixedThreadPool(12, THREAD_FACTORY));

    public Mono<Payment> createPayment(final String userId, BigDecimal valor) {
        final Payment payment = Payment
                .builder()
                .paymentId(UUID.randomUUID().toString())
                .data(LocalDateTime.now())
                .status(StatusPayment.PENDING)
                .userId(userId)
                .valor(valor.setScale(2, BigDecimal.ROUND_UP))
                .build();

        return Mono.fromCallable(() -> {
            log.info("Saving payment transaction for user {}", userId);
            return this.database.save(userId, payment);
        })
                .subscribeOn(Schedulers.boundedElastic())
                //.subscribeOn(SCHEDULER_DB)
                .doOnNext(pay -> log.info("Payment received for user {}", userId));
    }

    public Mono<Payment> getPayment(final String userId) {
        return Mono.defer(() -> {
            log.info("Getting payment for user {} in the database.", userId);
            final Optional<Payment> payment = this.database.get(userId, Payment.class);
            return Mono.justOrEmpty(payment);
        })
                .subscribeOn(Schedulers.boundedElastic())
                //.subscribeOn(SCHEDULER_DB)
                .doOnNext(it -> log.info("Payment recieved for user {} ", userId));
    }

    public Mono<Payment> processPayment(final String key, final StatusPayment statusAproved) {
        log.info("On payment {} received to status {} ", key, statusAproved);
        return getPayment(key)
                .flatMap(payment -> Mono.fromCallable(() -> {
                    log.info("Processing payment {} to status {} ", key, statusAproved);
                    return this.database.save(key, payment.withStatus(statusAproved));
                })
                        .subscribeOn(Schedulers.boundedElastic()));
                        //.subscribeOn(SCHEDULER_DB));
    }
}
