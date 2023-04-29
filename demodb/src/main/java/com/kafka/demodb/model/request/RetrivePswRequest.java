package com.kafka.demodb.model.request;

import lombok.Data;

@Data
public class RetrivePswRequest {

    private String email;
    private String trxId;
    private String otp;
    private String userKey;
}
