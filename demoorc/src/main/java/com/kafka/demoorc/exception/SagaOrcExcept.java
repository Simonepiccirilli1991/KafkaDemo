package com.kafka.demoorc.exception;

import lombok.Data;

@Data
public class SagaOrcExcept extends RuntimeException {

    private String errMsg;
    private String cause;

    public SagaOrcExcept() {
    }

    public SagaOrcExcept(String errMsg, String cause) {
        this.errMsg = errMsg;
        this.cause = cause;
    }
}
