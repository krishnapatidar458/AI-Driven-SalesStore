package com.SalesStore.SalesStoreApplication.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handle(ResourceNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }
}
