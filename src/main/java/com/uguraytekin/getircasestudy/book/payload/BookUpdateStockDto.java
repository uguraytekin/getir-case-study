package com.uguraytekin.getircasestudy.book.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateStockDto {

    private String bookId;

    private Integer newStock;
}
