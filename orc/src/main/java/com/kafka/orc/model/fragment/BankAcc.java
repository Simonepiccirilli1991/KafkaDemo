package com.kafka.orc.model.fragment;

import lombok.Data;

@Data
public class BankAcc {

    private String userKey;
    private int bankNumner;
    private Double amountAviable;
    private String email;
}
