package com.bloomberg.fxdeals.exception;

import com.bloomberg.fxdeals.dto.ApiResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateDealException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleDuplicateDealException(DuplicateDealException ex, WebRequest request) {
        log.error("Duplicate deal error: {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResponseDTO.error(ex.getMessage(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(InvalidDealDataException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidDealDataException(InvalidDealDataException ex, WebRequest request) {
        log.error("Invalid deal data error: {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResponseDTO.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.error("Validation error - {}: {}", fieldName, errorMessage);
        });

        String errorMessage = "Validation failed for input data";
        return new ResponseEntity<>(
                ApiResponseDTO.error(errorMessage, errors, HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage(),
                        (error1, error2) -> error1
                ));

        log.error("Constraint violations detected: {}", errors);
        String errorMessage = "Validation failed for input data";
        return new ResponseEntity<>(
                ApiResponseDTO.error(errorMessage, errors, HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unhandled exception occurred", ex);
        return new ResponseEntity<>(
                ApiResponseDTO.error(
                        "An unexpected error occurred. Please try again later.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}