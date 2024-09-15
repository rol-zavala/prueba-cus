package com.pruebacus.pruebacus.repositories;

import com.pruebacus.pruebacus.models.genders.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<GenderEntity, Long> {

    Optional<GenderEntity> findById(Long id);
    boolean existsGenderEntitiesById(Long id);

}
