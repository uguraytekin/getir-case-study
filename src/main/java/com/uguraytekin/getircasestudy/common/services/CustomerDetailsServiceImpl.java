package com.uguraytekin.getircasestudy.common.services;

import com.uguraytekin.getircasestudy.customer.models.Customer;
import com.uguraytekin.getircasestudy.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found with username: " + username));

        return CustomerDetailsImpl.build(customer);
    }

}
