package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class UserSicRequest {

    private String userKey;
    private String username;
    private String email;
    private String psw;
}
