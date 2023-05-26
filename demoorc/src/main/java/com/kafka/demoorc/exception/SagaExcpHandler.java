package com.kafka.demoorc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class SagaExcpHandler {

    // messo generalizzato per tutti, non mi andava di stare a farne 800 o mapper del ...
    @ExceptionHandler(SagaOrcExcept.class)
    ResponseEntity<SagaOrcError> erroHandler(SagaOrcExcept ex){

        SagaOrcError error = new SagaOrcError();
        error.setCause(error.getCause());
        error.setMsg(error.getMsg());
        error.setDate(LocalDateTime.now());

        return ResponseEntity.internalServerError().body(error);
    }
}
