package com.uguraytekin.getircasestudy.customer.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Getter
@Setter
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
