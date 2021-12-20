package com.uguraytekin.getircasestudy.common.exception;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

}
