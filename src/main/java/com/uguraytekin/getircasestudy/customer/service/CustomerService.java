package com.uguraytekin.getircasestudy.customer.service;

import com.uguraytekin.getircasestudy.customer.models.Customer;
import com.uguraytekin.getircasestudy.customer.payload.dto.CreateCustomerDto;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
public interface CustomerService {

    Customer save(CreateCustomerDto createCustomerDto);

    Page<OrderDto> getCustomerOrders(String customerId, Pageable pageable);
}
