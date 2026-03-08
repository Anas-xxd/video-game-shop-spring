package com.anasxxd.videogameshop.common;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(IllegalArgumentException ex) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", "Validation Error",
                "message", ex.getMessage()
        );
    }
}