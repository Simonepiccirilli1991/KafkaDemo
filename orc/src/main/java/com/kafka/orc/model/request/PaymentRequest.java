package com.kafka.orc.model.request;

import lombok.Data;

@Data
public class PaymentRequest {


    private String userPay;
    private String userReceive;
    private String item;
    private int itemAmount;
}
