package com.uguraytekin.getircasestudy.customer.repository;

import com.uguraytekin.getircasestudy.customer.models.ERole;
import com.uguraytekin.getircasestudy.customer.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
