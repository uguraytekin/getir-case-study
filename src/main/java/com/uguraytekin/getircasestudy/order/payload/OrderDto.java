package com.uguraytekin.getircasestudy.order.payload;

import lombok.*;

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
@Builder
@ToString
public class OrderDto {
    private String id;
    private LocalDateTime createAt;
    private String customerId;
    private List<OrderDetailDto> details;
    private BigDecimal totalPrice;
}
