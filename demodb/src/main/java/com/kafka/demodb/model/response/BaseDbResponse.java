package com.kafka.demodb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class BaseDbResponse {

    private String result;
    private String errMsg;
    private String errType;
    private String userKey;

    public BaseDbResponse(String result) {
        this.result = result;
    }
    public BaseDbResponse(String result, String userKey) {
        this.result = result;
        this.userKey = userKey;
    }
    public BaseDbResponse(String result, String errMsg, String errType) {
        this.result = result;
        this.errMsg = errMsg;
        this.errType = errType;
    }

    public BaseDbResponse() {
    }
}
