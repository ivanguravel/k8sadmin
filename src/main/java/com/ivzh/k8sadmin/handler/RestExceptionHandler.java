package com.ivzh.k8sadmin.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorInfo> handler(Exception e, WebRequest request) {
        log.debug(String.format("Handling...", e.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorInfo(e.getMessage()));
    }

    @Order(Integer.MIN_VALUE)
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorInfo> handlerLogin(Exception e, WebRequest request) {
        log.debug(String.format("Handling...", e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorInfo(e.getMessage()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorInfo implements Serializable {
        private String message;
    }
}
