package com.uguraytekin.getircasestudy.book.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateStockRequest {
    @NotNull
    @NotBlank
    private String bookId;

    @NotNull
    @Min(value = 0, message = "Stock cannot be less than zero!")
    private Integer newStock;
}
