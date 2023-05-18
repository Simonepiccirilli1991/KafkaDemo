package com.kafka.orc.model.request;

import com.kafka.orc.constants.Action;
import lombok.Data;

@Data
public class ValidationRequest {

    private Action action;
    private String otp;
    private String trxId;
    private String userKey;
}
