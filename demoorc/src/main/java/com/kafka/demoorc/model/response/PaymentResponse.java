package com.kafka.demoorc.model.response;

import lombok.Data;

@Data
public class PaymentResponse {

    private String resultMsg;
    private Boolean transaction;
    private String description;

    public PaymentResponse() {
    }

    public PaymentResponse(String resultMsg, Boolean transaction, String description) {
        this.resultMsg = resultMsg;
        this.transaction = transaction;
        this.description = description;
    }
}
