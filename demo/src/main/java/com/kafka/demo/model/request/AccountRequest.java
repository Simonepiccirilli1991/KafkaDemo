package com.kafka.demo.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountRequest {

    private String userKey;
    private String email;
    private String nome;
    private String cognome;
    private String type;
    private Double amountAviable;

}
