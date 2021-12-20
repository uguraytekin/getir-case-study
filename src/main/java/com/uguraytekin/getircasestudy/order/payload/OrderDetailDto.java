package com.uguraytekin.getircasestudy.order.payload;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    private String bookId;

    private Integer count;

    private BigDecimal pricePerUnit;
}
