package com.kafka.demo.model.request;

import lombok.Data;

@Data
public class PaymentRequest {

    private String userPay;
    private String userReceiv;
    private Double payAmount;
}
