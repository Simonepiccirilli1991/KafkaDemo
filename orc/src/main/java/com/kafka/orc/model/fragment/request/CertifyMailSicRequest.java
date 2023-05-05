package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class CertifyMailSicRequest {

    private String mail;
    private String userKey;
    private String trxId;
    private String otp;
}
