package com.kafka.orc.model.fragment.response;

import lombok.Data;

@Data
public class GetUserByUsernResponse {

    private String email;
    private String username;
    private String userKey;
}
