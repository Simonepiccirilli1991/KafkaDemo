package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class ChangePswSicRequest {

    private String trxId;
    private String otp;
    private String email;
    private String userKey;
    private String psw;
}
