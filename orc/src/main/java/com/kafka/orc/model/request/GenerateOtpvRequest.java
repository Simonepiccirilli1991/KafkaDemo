package com.kafka.orc.model.request;

import com.kafka.orc.constants.Action;
import lombok.Data;

@Data
public class GenerateOtpvRequest {

    private String userKey;
    private Action action;
}
