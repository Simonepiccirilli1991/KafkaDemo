package com.kafka.orc.model.fragment.response;

import lombok.Data;

@Data
public class BaseDbResponse {

    private String result;
    private String errMsg;
    private String errType;
    private String userKey;
}
