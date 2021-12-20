package com.uguraytekin.getircasestudy.statistics;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.order.payload.OrderDetailDto;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import com.uguraytekin.getircasestudy.statistics.payload.CustomerStatisticsDto;
import com.uguraytekin.getircasestudy.statistics.payload.StatisticDto;
import com.uguraytekin.getircasestudy.statistics.service.StatisticsFacade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatisticsFacadeUnitTest {

    @InjectMocks
    private StatisticsFacade statisticsFacade;
    @Mock
    private OrderFacade orderFacade;

    @Test
    void getStatisticsByCustomerId_OK() {
        List<OrderDto> orderList = Arrays.asList(
                OrderDto.builder().id("1").createAt(LocalDateTime.now()).totalPrice(BigDecimal.valueOf(10))
                        .details(List.of(OrderDetailDto.builder().bookId("1").count(2).build()))
                        .build(),
                OrderDto.builder().id("2").createAt(LocalDateTime.now()).totalPrice(BigDecimal.valueOf(10))
                        .details(List.of(OrderDetailDto.builder().bookId("1").count(2).build()))
                        .build());

        List<StatisticDto> statisticDtoList = Arrays.asList(StatisticDto.builder().totalBookCount(2).build(), StatisticDto.builder().totalBookCount(2).build());

        String customerId = "1";
        when(orderFacade.getOrders(customerId)).thenReturn(orderList);

        CustomerStatisticsDto statistic = statisticsFacade.getStatisticsByCustomerId(customerId);

        assertNotNull(statistic);
        assertEquals(statistic.getStatistics().stream().mapToInt(StatisticDto::getTotalBookCount).sum(), statisticDtoList.stream().mapToInt(StatisticDto::getTotalBookCount).sum());
    }


}
