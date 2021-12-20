package com.uguraytekin.getircasestudy.order.models;

import com.uguraytekin.getircasestudy.common.models.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Document(collection = "orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    private String id;

    @NotBlank
    private String customerId;

    @NotNull
    private List<OrderDetail> details;

    @NotNull
    private BigDecimal totalPrice;
}
