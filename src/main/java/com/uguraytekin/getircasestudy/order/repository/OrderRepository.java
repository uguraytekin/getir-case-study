package com.uguraytekin.getircasestudy.order.repository;

import com.uguraytekin.getircasestudy.order.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllByCustomerId(String customerId, Pageable pageable);

    List<Order> findAllByCustomerId(String customerId);

    Optional<Order> findByCustomerIdAndId(String customerId, String id);

    Page<Order> findAllByCustomerIdAndCreateAtBetween(String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
