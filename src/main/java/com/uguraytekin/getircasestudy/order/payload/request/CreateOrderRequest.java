package com.uguraytekin.getircasestudy.order.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private List<CreateOrderDetailRequest> orderDetails;
}
