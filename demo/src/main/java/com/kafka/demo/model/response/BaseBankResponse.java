package com.kafka.demo.model.response;

import lombok.Data;

@Data
public class BaseBankResponse {

    private String result;
    private String msg;
    private Boolean isError;

    //messo perche non sto usando il record, e il mapper si incazza se non trova costruttore vuoto in test
    public BaseBankResponse() {
    }

    public BaseBankResponse(String result, String msg, Boolean isError) {
        this.result = result;
        this.msg = msg;
        this.isError = isError;
    }
}
