package com.pruebacus.pruebacus.repositories.carts;

import com.pruebacus.pruebacus.models.carts.CartsProductsEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartsProductsRepository extends CrudRepository<CartsProductsEntity, String> {
}
