package com.uguraytekin.getircasestudy.order.payload.response;

import com.uguraytekin.getircasestudy.order.payload.OrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private LocalDateTime createAt;
    private String customerId;
    private List<OrderDetailDto> details;
    private BigDecimal totalPrice;
}
