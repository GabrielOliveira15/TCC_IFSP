package com.gabrieloliveira.springsecurity.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabrieloliveira.springsecurity.models.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleName(String name);

}
