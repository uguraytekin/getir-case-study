package com.uguraytekin.getircasestudy.order.service;


import com.uguraytekin.getircasestudy.book.service.BookServiceFacade;
import com.uguraytekin.getircasestudy.order.models.Order;
import com.uguraytekin.getircasestudy.order.payload.CreateOrderDetailDto;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.payload.request.CreateOrderDetailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Component
@RequiredArgsConstructor
@Log4j2
public class OrderFacade {

    private final OrderService orderService;
    private final BookServiceFacade bookServiceFacade;
    private final ModelMapper mapper;

    public Page<OrderDto> getOrders(String customerId, Pageable pageable) {
        log.info("Getting orders of Customer : {}, Page Index : {}, Page Size : {}", customerId, pageable.getPageNumber(), pageable.getPageSize());
        Page<Order> pageResult = orderService.findOrdersByCustomerId(customerId, pageable);
        return pageResult.map(order -> mapper.map(order, OrderDto.class));
    }

    public List<OrderDto> getOrders(String customerId) {
        log.info("Getting orders of Customer : {}", customerId);
        List<Order> result = orderService.findOrdersByCustomerId(customerId);
        return result.stream().map(order -> mapper.map(order, OrderDto.class)).toList();
    }

    public OrderDto createOrder(String customerId, List<CreateOrderDetailRequest> detailList) {
        log.info("Creating order started customerId:{} ", customerId);
        Order order = orderService.buy(customerId, detailList.stream().map(x -> CreateOrderDetailDto.builder()
                .book(bookServiceFacade.getById(x.getBookId()))
                .count(x.getCount())
                .build()).toList());

        return mapper.map(order, OrderDto.class);
    }

    public OrderDto getOrderById(String customerId, String orderId) {
        log.info("Get order started with id : {}", orderId);
        Order order = orderService.findCustomerIdAndId(customerId, orderId);
        OrderDto requestedOrder = mapper.map(order, OrderDto.class);
        log.info("Displayed Order is : {}", requestedOrder.toString());
        return requestedOrder;
    }

    public Page<OrderDto> getOrdersByDateInterval(String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("Get orders Date Interval started between dates of {} and {}", startDate, endDate);
        Page<Order> pageResult = orderService.findOrdersByCustomerIdAndDateInterval(customerId, startDate, endDate, pageable);
        return pageResult.map(order -> mapper.map(order, OrderDto.class));
    }
}
