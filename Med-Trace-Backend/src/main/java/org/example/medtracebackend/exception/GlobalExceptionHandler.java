package org.example.medtracebackend.exception;

import org.example.medtracebackend.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(new APIResponse<>
                (500, "Internal Server Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIResponse<String>> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(new APIResponse<>
                (HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Null values", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new APIResponse<>
                (HttpStatus.NOT_FOUND.value(), "Resource Not Found", ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}