package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class UserAccRequest {

    private String userKey;
    private String email;
    private String nome;
    private String cognome;
    private String type;
}
