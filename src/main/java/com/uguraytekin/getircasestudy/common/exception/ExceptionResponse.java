package com.uguraytekin.getircasestudy.common.exception;

import lombok.*;

import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private String timestamp;

    private String path;

    private Integer status;

    private List<String> message;
}
