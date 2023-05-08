package com.kafka.orc.model.fragment.response;

import lombok.Data;

@Data
public class BaseBankResponse {

    private String result;
    private String msg;
    private Boolean isError;
    private int accNumber;
}
