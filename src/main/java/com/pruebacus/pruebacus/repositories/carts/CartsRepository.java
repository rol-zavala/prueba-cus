package com.pruebacus.pruebacus.repositories.carts;

import com.pruebacus.pruebacus.models.carts.CartsEntity;
import com.pruebacus.pruebacus.models.genders.GenderEntity;
import com.pruebacus.pruebacus.models.users.ConfirmationUserTokenEntity;
import com.pruebacus.pruebacus.models.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartsRepository extends JpaRepository<CartsEntity, String> {

    CartsEntity findById(Long id);

    Boolean existsById(Long id);

    Optional<CartsEntity> deleteById(Long id);

    @Query(value = "SELECT * FROM carts WHERE user_id = :userId AND is_paid = :isPaid", nativeQuery = true)
    List<CartsEntity> findAllByUserAndPaid(Long userId, Boolean isPaid);
}
