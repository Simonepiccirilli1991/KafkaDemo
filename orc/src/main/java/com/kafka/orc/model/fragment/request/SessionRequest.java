package com.kafka.orc.model.fragment.request;

import lombok.Data;

@Data
public class SessionRequest {

    private String  key;
    private String userKey;
    private String scope;
}
