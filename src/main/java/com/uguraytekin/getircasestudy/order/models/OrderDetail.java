package com.uguraytekin.getircasestudy.order.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Builder
@Getter
@ToString
public class OrderDetail {

    private String bookId;

    private Integer count;

    private BigDecimal pricePerUnit;
}
