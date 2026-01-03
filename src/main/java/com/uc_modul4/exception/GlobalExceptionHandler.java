package com.uc_modul4.exception;

import com.uc_modul4.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // Trả về lỗi 400 (Bad Request) thay vì 500
        return ResponseHandler.generateResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                null
        );
    }

}