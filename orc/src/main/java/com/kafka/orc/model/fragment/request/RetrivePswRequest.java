package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class RetrivePswRequest {

    private String email;
    private String trxId;
    private String otp;
    private String userKey;
}
