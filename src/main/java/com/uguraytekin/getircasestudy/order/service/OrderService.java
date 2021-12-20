package com.uguraytekin.getircasestudy.order.service;

import com.uguraytekin.getircasestudy.order.models.Order;
import com.uguraytekin.getircasestudy.order.payload.CreateOrderDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

public interface OrderService {

    Page<Order> findOrdersByCustomerId(String customerId, Pageable pageable);

    List<Order> findOrdersByCustomerId(String customerId);

    Order findCustomerIdAndId(String customerId, String orderId);

    Page<Order> findOrdersByCustomerIdAndDateInterval(String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Order buy(String customerId, List<CreateOrderDetailDto> detailList);
}
