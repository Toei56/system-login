package com.example.login.testLogin.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidation(ValidateException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleUserDuplicate(BaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        errorResponse.setError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class ErrorResponse {
        private LocalDateTime timestamp = LocalDateTime.now();

        private int status;

        private String error;
    }
}
