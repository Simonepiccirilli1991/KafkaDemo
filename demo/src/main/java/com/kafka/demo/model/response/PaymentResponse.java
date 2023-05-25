package com.kafka.demo.model.response;

import lombok.Data;

@Data
public class PaymentResponse {

    private Boolean transaction;
    private String causeMgs;

    public PaymentResponse() {
    }

    public PaymentResponse(Boolean transaction, String causeMgs) {
        this.transaction = transaction;
        this.causeMgs = causeMgs;
    }
}
