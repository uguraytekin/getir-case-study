package com.uguraytekin.getircasestudy.customer.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
