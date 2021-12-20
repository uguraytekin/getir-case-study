package com.uguraytekin.getircasestudy.customer.repository;

import com.uguraytekin.getircasestudy.customer.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
