package com.uguraytekin.getircasestudy.order.service;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.repository.BookRepository;
import com.uguraytekin.getircasestudy.common.exception.EntityNotFoundException;
import com.uguraytekin.getircasestudy.common.exception.InsufficientStockException;
import com.uguraytekin.getircasestudy.order.models.Order;
import com.uguraytekin.getircasestudy.order.models.OrderDetail;
import com.uguraytekin.getircasestudy.order.payload.CreateOrderDetailDto;
import com.uguraytekin.getircasestudy.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<Order> findOrdersByCustomerId(String customerId, Pageable pageable) {
        log.info("Getting orders of customer {} with page number {} and pageSize {}.", customerId, pageable.getPageNumber(), pageable.getPageSize());
        Page<Order> page = orderRepository.findAllByCustomerId(customerId, pageable);
        log.info("Page returned with total pages : {}, total elements : {}", page.getTotalPages(), page.getTotalElements());
        return page;
    }

    @Override
    public List<Order> findOrdersByCustomerId(String customerId) {
        log.info("Getting orders of customer {}.", customerId);
        List<Order> page = orderRepository.findAllByCustomerId(customerId);
        log.info("returned with total elements : {}", page.size());
        return page;
    }

    @Override
    public Order findCustomerIdAndId(String customerId, String orderId) {
        log.info("Trying to find order with id: {}", orderId);
        Order order = orderRepository.findByCustomerIdAndId(customerId, orderId).orElseThrow(() -> new EntityNotFoundException("Order doesnt exists!"));
        log.info("Order found: {}", order.toString());
        return order;
    }

    @Override
    public Page<Order> findOrdersByCustomerIdAndDateInterval(String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("Getting orders between {} and {}.", startDate, endDate);
        Page<Order> page = orderRepository.findAllByCustomerIdAndCreateAtBetween(customerId, startDate, endDate, pageable);
        log.info("Page returned with total pages : {}, total elements : {}", page.getTotalPages(), page.getTotalElements());
        return page;
    }

    @Override
    @Retryable(value = {OptimisticLockingFailureException.class})
    @Transactional(rollbackFor = Exception.class)
    public Order buy(String customerId, List<CreateOrderDetailDto> detailList) {
        log.info("Trying to buy order: {}", detailList.toString());
        for (CreateOrderDetailDto orderDetail : detailList) {
            Book book = bookRepository.findByIdAndStockGreaterThanEqual(orderDetail.getBook().getId(), orderDetail.getCount()).orElseThrow(() -> new InsufficientStockException("Insufficient Stocks!"));
            book.setStock(book.getStock() - orderDetail.getCount());
            bookRepository.save(book);
        }

        Order order = Order.builder().customerId(customerId).details(detailList.stream().map(detail -> OrderDetail.builder()
                        .bookId(detail.getBook().getId())
                        .count(detail.getCount())
                        .pricePerUnit(detail.getBook().getPrice())
                        .build()).toList())
                .totalPrice(detailList.stream().map(x -> x.getBook().getPrice().multiply(BigDecimal.valueOf(x.getCount()))).reduce(BigDecimal.ZERO, BigDecimal::add)).build();
        orderRepository.save(order);

        log.info("Order bought. {}", order.toString());
        return order;
    }
}
