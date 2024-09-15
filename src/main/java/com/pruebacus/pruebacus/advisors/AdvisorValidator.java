package com.pruebacus.pruebacus.advisors;

public interface AdvisorValidator<T, K> {
    K validate(T entity);
}
