package com.kafka.otpv.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OtpvDto implements Serializable {

    private String trxId;
    private String otp;
    private String userKey;
    private LocalDateTime startTime;
}
