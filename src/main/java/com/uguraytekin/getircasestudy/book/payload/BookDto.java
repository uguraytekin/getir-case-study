package com.uguraytekin.getircasestudy.book.payload;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
}
