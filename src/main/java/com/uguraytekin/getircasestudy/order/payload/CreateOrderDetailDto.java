package com.uguraytekin.getircasestudy.order.payload;

import com.uguraytekin.getircasestudy.book.models.Book;
import lombok.*;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailDto {

    private Book book;

    private Integer count;
}
