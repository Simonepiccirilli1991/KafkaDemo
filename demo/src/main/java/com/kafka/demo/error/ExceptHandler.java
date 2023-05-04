package com.kafka.demo.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptHandler {


    @ExceptionHandler(CustomExcept.class)
    public ResponseEntity<ErrorExc> expcHandler(CustomExcept ex){

        ErrorExc resp = new ErrorExc();
        resp.setCause(ex.getCause());
        resp.setMsg(ex.getMsg());
        resp.setData(LocalDateTime.now());

        return new ResponseEntity<>(resp,ex.getStatus());
    }
}
