package com.uguraytekin.getircasestudy.order.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailRequest {
    @NotBlank
    private String bookId;

    @NotNull
    @Size(min = 1)
    private Integer count;
}
