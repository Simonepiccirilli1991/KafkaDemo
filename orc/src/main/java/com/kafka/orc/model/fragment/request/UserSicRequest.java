package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class UserRequest {

    private String userKey;
    private String username;
    private String email;
    private String psw;
}
