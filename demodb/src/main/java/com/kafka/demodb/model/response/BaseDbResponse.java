package com.kafka.demodb.model.response;

import lombok.Data;

@Data
public class BaseDbResponse {

    private String result;
    private String errMsg;
    private String errType;

    public BaseDbResponse(String result) {
        this.result = result;
    }

    public BaseDbResponse(String result, String errMsg, String errType) {
        this.result = result;
        this.errMsg = errMsg;
        this.errType = errType;
    }

    public BaseDbResponse() {
    }
}
