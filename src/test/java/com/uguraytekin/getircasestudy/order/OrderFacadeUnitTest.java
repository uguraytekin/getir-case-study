package com.uguraytekin.getircasestudy.order;


import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.service.BookServiceFacade;
import com.uguraytekin.getircasestudy.order.models.Order;
import com.uguraytekin.getircasestudy.order.models.OrderDetail;
import com.uguraytekin.getircasestudy.order.payload.OrderDetailDto;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.payload.request.CreateOrderDetailRequest;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import com.uguraytekin.getircasestudy.order.service.OrderServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@ExtendWith(MockitoExtension.class)
class OrderFacadeUnitTest {

    @InjectMocks
    private OrderFacade orderFacade;
    @Mock
    private BookServiceFacade bookServiceFacade;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private ModelMapper mapper;

    Order order;
    OrderDto orderDto;
    String customerId = "1";

    @BeforeEach
    void setUp() {

        order = Order.builder()
                .id("1")
                .details(List.of(OrderDetail.builder().bookId("1").count(2).pricePerUnit(BigDecimal.valueOf(9)).build()))
                .build();

        orderDto = OrderDto.builder()
                .id("1")
                .details(List.of(OrderDetailDto.builder().bookId("1").count(2).pricePerUnit(BigDecimal.valueOf(9)).build()))
                .build();
    }

    @Test
    void getOrdersWithPage_OK() {
        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());
        Page<Order> pageOrderList = new PageImpl<>(orderList);
        PageRequest pageable = PageRequest.of(0, 10);

        when(orderService.findOrdersByCustomerId(customerId, pageable)).thenReturn(pageOrderList);
        when(mapper.map(any(), any())).thenReturn(orderDto);

        Page<OrderDto> orders = orderFacade.getOrders(customerId, pageable);

        assertNotNull(orders);
        assertEquals(orders.getSize(), pageOrderList.getSize());
    }

    @Test
    void getOrders_OK() {

        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());

        when(orderService.findOrdersByCustomerId(customerId)).thenReturn(orderList);
        when(mapper.map(any(), any())).thenReturn(orderDto);

        List<OrderDto> orders = orderFacade.getOrders(customerId);

        assertNotNull(orders);
        assertEquals(orders.size(), orderList.size());
    }

    @Test
    void createOrder_OK() {
        Book book = Book.builder().id("1").price(BigDecimal.valueOf(9)).stock(10).build();
        List<CreateOrderDetailRequest> detailList = List.of(CreateOrderDetailRequest.builder().bookId(book.getId()).count(2).build());

        when(orderService.buy(any(), any())).thenReturn(order);
        when(bookServiceFacade.getById(book.getId())).thenReturn(book);
        when(mapper.map(any(), any())).thenReturn(orderDto);

        OrderDto order = orderFacade.createOrder(customerId, detailList);

        assertNotNull(order);
        assertEquals(order.getDetails().size(), detailList.size());
        assertEquals(order.getDetails().stream().mapToInt(OrderDetailDto::getCount).sum(), detailList.stream().mapToInt(CreateOrderDetailRequest::getCount).sum());
    }

    @Test
    void getOrderById_OK() {
        String orderId = "1";
        when(orderService.findCustomerIdAndId(customerId, orderId)).thenReturn(order);
        when(mapper.map(order, OrderDto.class)).thenReturn(orderDto);

        OrderDto dbOrder = orderFacade.getOrderById(customerId, orderId);
        assertNotNull(order);
        assertEquals(orderId, dbOrder.getId());
    }

    @Test
    void getOrdersByDateInterval_OK() {

        String customerId = "1";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        PageRequest pageable = PageRequest.of(0, 10);

        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());
        Page<Order> pageOrderList = new PageImpl<>(orderList);

        when(orderService.findOrdersByCustomerIdAndDateInterval(customerId, startDate, endDate, pageable)).thenReturn(pageOrderList);
        when(mapper.map(any(), any())).thenReturn(orderDto);

        Page<OrderDto> orders = orderFacade.getOrdersByDateInterval(customerId, startDate, endDate, pageable);

        assertNotNull(orders);
        assertEquals(orders.getSize(), orderList.size());
    }
}
