package com.SalesStore.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    public Map<String,String> handleUserExists(UserAlreadyExistsException userAlreadyExistsException){
        return Map.of("error", userAlreadyExistsException.getMessage());
    }

    public Map<String,String> handleInvaildCredentials(InvalidCredentialsException invalidCredentialsException){
        return Map.of("error",invalidCredentialsException.getMessage());
    }

}
