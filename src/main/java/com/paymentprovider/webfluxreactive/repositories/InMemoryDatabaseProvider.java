package com.paymentprovider.webfluxreactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class InMemoryDatabaseProvider implements Database {

    public static final Map<String, String> DATABASE = new ConcurrentHashMap<>();
    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public <T> T save(final String key, final T value) {
        final String dataJson = this.mapper.writeValueAsString(value);
        DATABASE.put(key, dataJson);
        // sleep(30L); // Thread Sleep Milissegundos
        return value;
    }

    @Override
    public <T> Optional<T> get(final String key, final Class<T> clazz) {
        final String dataJson = this.DATABASE.get(key);

       // sleep(15L); // Thread Sleep Milissegundos

        return Optional.ofNullable(dataJson)
                .map(data -> {
                    try {
                        return mapper.readValue(data, clazz);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    private void sleep(Long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
