package com.kafka.otpv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class ExceptionOtpvHandler {

    public static final String LOGIC_PREFIX = "otpv0.logic.";
    private static final String GENERIC = "otpv0.generic";

    // generic expeption handler
    @ExceptionHandler(OtpvError.class)
    public ResponseEntity<ApiError> actionError(OtpvError ex){


        ApiError response = new ApiError();

        response.setErrMsg(ex.getErrMsg());
        response.setErrTp(ex.getErrTp());
        response.setTimeKo(ex.getTimeKo());
        response.setApiAcronim(LOGIC_PREFIX);

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ApiError> timeout(){

        ApiError response = new ApiError();

        response.setErrMsg("Otpv is expired, resend and retry");
        response.setErrTp("Otp_Epired");
        response.setTimeKo(LocalDateTime.now());
        response.setApiAcronim(LOGIC_PREFIX);

        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }
}
