package com.kafka.cache.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CacheErrorHandler {


    // non mi andava di mappare o farne tot , quindi torna sempre 500 in caso di errore
    @ExceptionHandler(SessionError.class)
    public ResponseEntity<CacheError> sessionErrorHandler(SessionError ex){
        var resp = new CacheError("errFrom",ex.getMsg(),ex.getCaused(), LocalDateTime.now());
        return ResponseEntity.internalServerError().body(resp);
    }
}
