package com.kafka.otpv.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private String errMsg;
    private String errTp;
    private LocalDateTime timeKo;
    private String apiAcronim;
}
