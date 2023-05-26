package com.kafka.demoorc.model.fragment.request;

import lombok.Data;

@Data
public class TransactionPaymentRequest {

    private String userPay;
    private String userReceiv;
    private Double payAmount;
}
