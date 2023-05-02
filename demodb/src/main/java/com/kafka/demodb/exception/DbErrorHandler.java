package com.kafka.demodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DbErrorHandler {

    private static final String GENERIC = "dbsaga.generic";
    public static final String LOGIC_PREFIX = "dbsaga.logic.";
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenerciErrResponse> genericRespone(){
        return new ResponseEntity<>(new GenerciErrResponse("DbSaga Error, retry after", "Generic_Error", "dbsaga"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomError.class)
    ResponseEntity<CustomExceptionResponse> customError(CustomError ex){
        return new ResponseEntity<>(mapCustomExcept(ex),ex.getStatus());
    }

    record GenerciErrResponse(String errMgs, String errTp, String from){}

    public record CustomExceptionResponse(String errMgs, String errTp, String from, LocalDateTime time){};


    private CustomExceptionResponse mapCustomExcept(CustomError ex){
        return new CustomExceptionResponse(ex.getErrMsg(),ex.getErrTp(),LOGIC_PREFIX,ex.getTime());
    }
}





