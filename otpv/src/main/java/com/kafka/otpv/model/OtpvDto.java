package com.kafka.otpv.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpvDto {

    private String trxId;
    private String otp;
    private String userKey;
    private LocalDateTime startTime;
}
