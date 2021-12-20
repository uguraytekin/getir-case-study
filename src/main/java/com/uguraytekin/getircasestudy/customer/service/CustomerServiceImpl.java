package com.uguraytekin.getircasestudy.customer.service;

import com.uguraytekin.getircasestudy.common.exception.BusinessException;
import com.uguraytekin.getircasestudy.customer.models.Customer;
import com.uguraytekin.getircasestudy.customer.models.ERole;
import com.uguraytekin.getircasestudy.customer.models.Role;
import com.uguraytekin.getircasestudy.customer.payload.dto.CreateCustomerDto;
import com.uguraytekin.getircasestudy.customer.repository.CustomerRepository;
import com.uguraytekin.getircasestudy.customer.repository.RoleRepository;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final ModelMapper mapper;

    private final OrderFacade orderFacade;

    @Override
    public Customer save(CreateCustomerDto createCustomerDto) {

        if (Boolean.TRUE.equals(customerRepository.existsByUsername(createCustomerDto.getUsername()))) {
            throw new BusinessException("Username is already taken!");
        }

        if (Boolean.TRUE.equals(customerRepository.existsByEmail(createCustomerDto.getEmail()))) {
            throw new BusinessException("Email is already in use!");
        }

        // Create new customer's account
        createCustomerDto.setPassword(encoder.encode(createCustomerDto.getPassword()));
        Customer customer = mapper.map(createCustomerDto, Customer.class);
        Set<Role> roles = new HashSet<>();

        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElse(roleRepository.save(Role.builder().name(ERole.ROLE_CUSTOMER).build()));
        roles.add(customerRole);

        customer.setRoles(roles);
        customerRepository.save(customer);

        return customer;
    }

    public Page<OrderDto> getCustomerOrders(String customerId, Pageable pageable) {
        log.info("Getting orders of customer started with customer : {}", customerId);
        return orderFacade.getOrders(customerId, pageable);
    }
}