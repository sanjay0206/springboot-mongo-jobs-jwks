package com.authorizationserver.repository;

import com.authorizationserver.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {
    List<Role> findByNameIn(List<String> names);
}
