package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class BalanceRequest {

    private String userKey;
    private Double amount;
}
