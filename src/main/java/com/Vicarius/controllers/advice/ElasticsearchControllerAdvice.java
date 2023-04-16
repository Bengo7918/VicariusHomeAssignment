package com.Vicarius.controllers.advice;

import com.Vicarius.controllers.advice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ElasticsearchControllerAdvice {

    @ExceptionHandler(BookStoreServerErrorException.class)
    public ResponseEntity<ErrorResponse> handle(BookStoreServerErrorException ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(BookStoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(BookStoreNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(BookStoreAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(BookStoreAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }
}