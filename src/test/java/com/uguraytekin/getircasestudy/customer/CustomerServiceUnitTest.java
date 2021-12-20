package com.uguraytekin.getircasestudy.customer;

import com.uguraytekin.getircasestudy.customer.models.Customer;
import com.uguraytekin.getircasestudy.customer.payload.dto.CreateCustomerDto;
import com.uguraytekin.getircasestudy.customer.repository.CustomerRepository;
import com.uguraytekin.getircasestudy.customer.repository.RoleRepository;
import com.uguraytekin.getircasestudy.customer.service.CustomerServiceImpl;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {

    @InjectMocks
    CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private OrderFacade orderFacade;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("id")
                .username("test")
                .password("passasdasd")
                .email("email")
                .build();
    }

    @Test
    void signupCustomer_OK() {
        CreateCustomerDto createCustomerDto = CreateCustomerDto.builder()
                .username("test").password("pass")
                .email("email")
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);
        when(encoder.encode(createCustomerDto.getPassword())).thenReturn(customer.getPassword());
        when(mapper.map(createCustomerDto, Customer.class)).thenReturn(customer);

        Customer savedCustomer = customerService.save(createCustomerDto);

        assertEquals(savedCustomer, customer);
        assertEquals(customer.getUsername(), savedCustomer.getUsername());
        assertEquals(customer.getEmail(), savedCustomer.getEmail());
        assertEquals(customer.getPassword(), savedCustomer.getPassword());
        assertEquals(customer.getId(), savedCustomer.getId());
    }

    @Test
    void getCustomerOrders_OK() {

        List<OrderDto> orderList = Arrays.asList(OrderDto.builder().id("1").build(), OrderDto.builder().id("2").build());
        Page<OrderDto> pageOrderList = new PageImpl<>(orderList);
        PageRequest pageable = PageRequest.of(0, 10);

        when(orderFacade.getOrders(customer.getId(), pageable)).thenReturn(pageOrderList);
        Page<OrderDto> list = customerService.getCustomerOrders(customer.getId(), pageable);

        assertNotNull(list);
        assertEquals(list.getSize(), pageOrderList.getSize());
    }
}
