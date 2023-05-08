package com.kafka.orc.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorOrcHandler {

    @ExceptionHandler(OrcError.class)
    public ResponseEntity<ErrorDto> manageGeneralError(OrcError ex){
            //TODO: inserire logica
        return new ResponseEntity<>(new ErrorDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
