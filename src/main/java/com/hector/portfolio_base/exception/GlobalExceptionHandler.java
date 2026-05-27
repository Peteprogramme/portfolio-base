package com.hector.portfolio_base.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global interceptor for application exceptions.
 * This class catches errors before they reach the client and formats them into clean JSON responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catches database integrity violations, such as attempting to insert a duplicate SKU.
     * Returns a 409 Conflict status instead of a 500 Internal Server Error.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateSKU(DataIntegrityViolationException ex) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Database Conflict");
        errorResponse.put("message", "A part with this SKU already exists. SKUs must be unique.");

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}