package com.workintech.twitterapi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception) {

        ApiErrorResponse response = new ApiErrorResponse(
                exception.getMessage(),
                exception.getStatus().value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ApiErrorResponse response = new ApiErrorResponse(
                message,
                400,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception exception) {

        ApiErrorResponse response = new ApiErrorResponse(
                exception.getMessage(),
                500,
                LocalDateTime.now()
        );

        return ResponseEntity.internalServerError().body(response);
    }
}