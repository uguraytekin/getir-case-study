package com.uguraytekin.getircasestudy.order;


import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.repository.BookRepository;
import com.uguraytekin.getircasestudy.order.models.Order;
import com.uguraytekin.getircasestudy.order.models.OrderDetail;
import com.uguraytekin.getircasestudy.order.payload.CreateOrderDetailDto;
import com.uguraytekin.getircasestudy.order.repository.OrderRepository;
import com.uguraytekin.getircasestudy.order.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookRepository bookRepository;

    Order order;

    @BeforeEach
    void setUp() {

        order = Order.builder()
                .id("id")
                .details(List.of(OrderDetail.builder().bookId("1").count(2).pricePerUnit(BigDecimal.valueOf(9)).build()))
                .build();
    }

    @Test
    void findOrdersByCustomerIdWithPage_OK() {
        String customerId = "1";
        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());
        Page<Order> pageOrderList = new PageImpl<>(orderList);
        PageRequest pageable = PageRequest.of(0, 10);

        when(orderRepository.findAllByCustomerId(customerId, pageable)).thenReturn(pageOrderList);

        Page<Order> orders = orderService.findOrdersByCustomerId(customerId, pageable);

        assertNotNull(orders);
        assertEquals(orders.getSize(), pageOrderList.getSize());
    }

    @Test
    void findOrdersByCustomerId_OK() {
        String customerId = "1";
        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());

        when(orderRepository.findAllByCustomerId(customerId)).thenReturn(orderList);

        List<Order> orders = orderService.findOrdersByCustomerId(customerId);

        assertNotNull(orders);
        assertEquals(orders.size(), orderList.size());
    }

    @Test
    void findCustomerIdAndId_OK() {
        String customerId = "1";
        String orderId = "1";

        when(orderRepository.findByCustomerIdAndId(customerId, orderId)).thenReturn(Optional.ofNullable(order));

        Order orderItem = orderService.findCustomerIdAndId(customerId, orderId);

        assertNotNull(orderItem);
        assertEquals(order, orderItem);
    }

    @Test
    void findOrdersByCustomerIdAndDateInterval_OK() {
        String customerId = "1";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        PageRequest pageable = PageRequest.of(0, 10);

        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());
        Page<Order> pageOrderList = new PageImpl<>(orderList);

        when(orderRepository.findAllByCustomerIdAndCreateAtBetween(customerId, startDate, endDate, pageable)).thenReturn(pageOrderList);

        Page<Order> orders = orderService.findOrdersByCustomerIdAndDateInterval(customerId, startDate, endDate, pageable);

        assertNotNull(orders);
        assertEquals(orders.getSize(), orderList.size());
    }


    @Test
    void buy_OK() {
        String customerId = "1";

        Book book = Book.builder().id("1").price(BigDecimal.valueOf(9)).stock(10).build();

        List<CreateOrderDetailDto> detailList = List.of(CreateOrderDetailDto.builder().book(book).count(2).build());

        when(bookRepository.findByIdAndStockGreaterThanEqual(any(), any())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(orderRepository.save(any())).thenReturn(order);

        Order newOrder = orderService.buy(customerId, detailList);
        assertNotNull(newOrder);
        assertEquals(newOrder.getDetails().size(), detailList.size());
        assertEquals(newOrder.getTotalPrice(), detailList.stream().map(x -> x.getBook().getPrice().multiply(BigDecimal.valueOf(x.getCount()))).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Test
    public void buy_OptimisticLockingFailureException() {

        String customerId = "1";

        Book book = Book.builder().id("1").price(BigDecimal.valueOf(9)).stock(10).build();

        List<CreateOrderDetailDto> detailList = List.of(CreateOrderDetailDto.builder().book(book).count(2).build());

        when(bookRepository.findByIdAndStockGreaterThanEqual(any(), any())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenThrow(OptimisticLockingFailureException.class);
        try {
            Order newOrder = orderService.buy(customerId, detailList);
        } catch (OptimisticLockingFailureException e) {
            System.out.println("Exception caught");
        }

        verify(bookRepository, times(1)).save(book);
    }

}
