package com.pruebacus.pruebacus.repositories;

import com.pruebacus.pruebacus.models.users.ConfirmationUserTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmationUserTokenRepository extends CrudRepository<ConfirmationUserTokenEntity, String> {

    ConfirmationUserTokenEntity findByConfirmationToken(String confirmationToken);
}
