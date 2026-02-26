package com.readdit.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.readdit.dto.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Response> handleNotFound(EmptyResultDataAccessException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("Resource not found"));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Response> handleDuplicate(DuplicateKeyException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.error("Duplicate entry"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response.error ("Something went wrong: " + e.getMessage()));
    }
}
