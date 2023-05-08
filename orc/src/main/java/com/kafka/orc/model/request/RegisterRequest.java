package com.kafka.orc.model.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String userKey;
    private String username;
    private String email;
    private String psw;
    private String cognome;
    private String type;
}
