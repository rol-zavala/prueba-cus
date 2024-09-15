package com.pruebacus.pruebacus.repositories;

import com.pruebacus.pruebacus.models.roles.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {


}
