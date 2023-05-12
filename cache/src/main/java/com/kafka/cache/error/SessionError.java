package com.kafka.cache.error;

import lombok.Data;

@Data
public class SessionError extends RuntimeException{

    private String msg;
    private String caused;


    public SessionError(String msg, String caused) {
        this.msg = msg;
        this.caused = caused;
    }
}
