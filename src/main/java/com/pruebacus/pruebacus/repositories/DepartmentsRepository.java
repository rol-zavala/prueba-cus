package com.pruebacus.pruebacus.repositories;

import com.pruebacus.pruebacus.models.departments.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentsRepository extends JpaRepository<DepartmentEntity, Long> {

    Optional<DepartmentEntity> findById(Long id);
    boolean existsDepartmentEntitiesById(Long id);
}

