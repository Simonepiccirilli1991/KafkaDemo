package com.kafka.cache.model.request;

import lombok.Data;

@Data
public class SessionRequest {

    private String  key;
    private String userKey;
    private String scope;
}
