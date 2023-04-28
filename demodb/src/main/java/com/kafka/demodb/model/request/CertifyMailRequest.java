package com.kafka.demodb.model.request;

import lombok.Data;

@Data
public class CertifyMailRequest {

    private String mail;
    private String userKey;
    private String trxId;
    private String otp;
}
