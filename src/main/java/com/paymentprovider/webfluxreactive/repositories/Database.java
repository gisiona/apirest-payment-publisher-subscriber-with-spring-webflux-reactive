package com.paymentprovider.webfluxreactive.repositories;

import java.util.Optional;

public interface Database {
    <T> T save(final String key, final T value);
    <T> Optional<T> get(final String key, Class<T> clazz);
}
