package com.uguraytekin.getircasestudy.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest httpRequest) {
        return getResponse(ex, httpRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex, HttpServletRequest httpRequest) {
        return getResponse(ex, httpRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ExceptionResponse> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest httpRequest) {
        return getResponse(ex, httpRequest, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleBookNotFound(BadRequestException ex, HttpServletRequest httpRequest) {
        return getResponse(ex, httpRequest, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> getResponse(RuntimeException ex, HttpServletRequest req, HttpStatus status) {

        return ResponseEntity.status(status).body(ExceptionResponse.builder()
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")))
                .path(req.getRequestURI())
                .status(status.value())
                .message(Collections.singletonList(ex.getMessage()))
                .build());
    }
}
