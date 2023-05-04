package com.kafka.demo.model.response;

import lombok.Data;

@Data
public class BaseBankResponse {

    private String result;
    private String msg;
    private Boolean isError = false;

    public BaseBankResponse(String result, String msg, Boolean isError) {
        this.result = result;
        this.msg = msg;
        this.isError = isError;
    }
}
