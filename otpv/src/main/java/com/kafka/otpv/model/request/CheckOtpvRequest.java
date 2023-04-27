package com.kafka.otpv.model.request;

import lombok.Data;

@Data
public class CheckOtpvRequest {

    private String trxId;
    private String otp;
    private String userKey;
}
