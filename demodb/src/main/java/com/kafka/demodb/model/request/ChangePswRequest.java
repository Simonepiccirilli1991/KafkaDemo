package com.kafka.demodb.model.request;

import lombok.Data;

@Data
public class ChangePswRequest {

    private String trxId;
    private String otp;
    private String email;
    private String userKey;
    private String psw;
}
