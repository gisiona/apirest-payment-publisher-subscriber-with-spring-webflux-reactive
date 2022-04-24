package com.paymentprovider.webfluxreactive.listenners;

import com.paymentprovider.webfluxreactive.models.PubSubMessage;
import com.paymentprovider.webfluxreactive.models.StatusPayment;
import com.paymentprovider.webfluxreactive.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener implements InitializingBean {

    private final Sinks.Many<PubSubMessage> sink;
    private final PaymentRepository paymentRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sink.asFlux()
                .subscribe(
                        mext -> {
                            log.info("On next message - {} ", mext.getKey());
                            this.paymentRepository.processPayment(mext.getKey(), StatusPayment.APPROVED)
                                    .subscribe();
                        },
                        error -> {
                            log.error("On pubsub listener observe error ", error);
                        },
                        () -> {
                            log.error("On pubsub listener complete ");
                        }
                );
    }
}
