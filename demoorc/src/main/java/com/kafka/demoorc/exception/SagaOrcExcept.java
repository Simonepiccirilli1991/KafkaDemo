package com.kafka.demoorc.exception;

import lombok.Data;

@Data
public class SagaOrcExcept extends RuntimeException {

    private String errMsg;
    private String caused;

    public SagaOrcExcept() {
    }

    public SagaOrcExcept(String errMsg, String caused) {
        this.errMsg = errMsg;
        this.caused = caused;
    }
}
