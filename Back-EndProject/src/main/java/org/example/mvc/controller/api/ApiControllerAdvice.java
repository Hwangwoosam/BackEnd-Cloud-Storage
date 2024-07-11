package org.example.mvc.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handValidationExceptions(MethodArgumentNotValidException validException){
        Map<String, String> errors = new HashMap<>();
        validException.getBindingResult().getAllErrors()
                .forEach(c -> errors.put("message", c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}