package com.uguraytekin.getircasestudy.book.payload.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private String id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
}
