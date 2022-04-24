package com.paymentprovider.webfluxreactive;

import com.paymentprovider.webfluxreactive.models.PubSubMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class PaymentProviderWebfluxReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProviderWebfluxReactiveApplication.class, args);
	}

	@Bean
	public Sinks.Many<PubSubMessage> sink() {
		return Sinks.many().multicast()
				.onBackpressureBuffer(2000);
	}
}
