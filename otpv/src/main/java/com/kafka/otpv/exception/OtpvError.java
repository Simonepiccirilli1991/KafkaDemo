package com.kafka.otpv.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class OtpvError extends RuntimeException{

    private HttpStatus status;
    private String errMsg;
    private String errTp;
    private LocalDateTime timeKo = LocalDateTime.now();

    public OtpvError(HttpStatus status, String errMsg, String errTp) {
        this.status = status;
        this.errMsg = errMsg;
        this.errTp = errTp;
    }
}
