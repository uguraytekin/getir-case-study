package com.uguraytekin.getircasestudy.customer.payload.dto;

import lombok.*;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto {

    private String username;

    private String email;

    private String password;
}
