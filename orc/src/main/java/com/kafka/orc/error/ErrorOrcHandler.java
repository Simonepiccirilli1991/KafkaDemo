package com.kafka.orc.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorOrcHandler {

    @ExceptionHandler(OrcError.class)
    public ResponseEntity<ErrorDto> manageGeneralError(OrcError ex){

            ErrorDto response = new ErrorDto();
            var map = errorMapper(ex.getErrId());
            response.setMsg(map.errMsg);
            response.setCause(ex.getCaused());
            response.setTime(LocalDateTime.now());

        return new ResponseEntity<>(response, map.status);
    }


    private static ErrorMapperDTO errorMapper(String errId) {
        if(errId == null) errId = "EMPTY";
        String errMsg = null;
        HttpStatus httpStatus = null;

        switch (errId) {
            case "Invalid_Request":
            case "CustomerId is mandatory":		 httpStatus = HttpStatus.BAD_REQUEST;	        errMsg = "Invalid request, missing parameter"; break;
            case "RegisterKO-01" : 			     httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; errMsg = "User already exist"; break;
            case "UserRegKO-01" :
            case "UserRegKO-02" :                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; errMsg = "Error on register User"; break;
            case "UserCertKO-01" :
            case "UserCertKO-02" : 		         httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; errMsg = "Error on certifyUser"; break;
            case "UserGetByKO-02": 				 httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; errMsg = "Error on getting User"; break;
            case "UserBankRegKO-01":
            case "UserBankRegKO-02":             httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; errMsg = "Error on register bank account"; break;

            default:
                errMsg = "Generic Error";
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                break;


        }

        return new ErrorMapperDTO(errMsg, httpStatus);
    }

    record ErrorMapperDTO(String errMsg,HttpStatus status){}
}
