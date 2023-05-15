package com.kafka.orc.model.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String userKey;
    private String psw;
}
