package com.uguraytekin.getircasestudy.book.payload.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class BookCreateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @Min(value = 0, message = "Price cannot be less than zero!")
    private BigDecimal price;
    @NotNull
    @Min(value = 0, message = "Stock cannot be less than zero!")
    private Integer stock;
}
