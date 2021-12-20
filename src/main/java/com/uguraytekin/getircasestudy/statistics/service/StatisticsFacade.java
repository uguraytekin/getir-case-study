package com.uguraytekin.getircasestudy.statistics.service;

import com.uguraytekin.getircasestudy.common.exception.EntityNotFoundException;
import com.uguraytekin.getircasestudy.order.payload.OrderDetailDto;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import com.uguraytekin.getircasestudy.statistics.payload.CustomerStatisticsDto;
import com.uguraytekin.getircasestudy.statistics.payload.StatisticDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */
@Component
@Log4j2
public record StatisticsFacade(OrderFacade orderFacade) {

    public CustomerStatisticsDto getStatisticsByCustomerId(String customerId) {
        log.info("Getting statistics of Customer started with id : {}", customerId);
        List<OrderDto> orderList = orderFacade.getOrders(customerId);

        if (orderList.isEmpty())
            throw new EntityNotFoundException("This customer has not have any orders");
        Map<String, StatisticDto> map = new HashMap<>();

        for (OrderDto orderDto : orderList) {
            String month = orderDto.getCreateAt().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            map.computeIfAbsent(month, x -> StatisticDto.builder().month(month).totalPurchasedAmount(BigDecimal.ZERO).build());

            StatisticDto statisticDto = map.get(month);
            statisticDto.incrementOrder();
            statisticDto.addPurchaseAmount(orderDto.getTotalPrice());
            statisticDto.addBook(orderDto.getDetails().stream().mapToInt(OrderDetailDto::getCount).sum());

            map.put(month, statisticDto);
        }

        log.info("Getting statistics of Customer finished");
        return CustomerStatisticsDto.builder()
                .statistics(map.values().stream().toList())
                .build();
    }
}
