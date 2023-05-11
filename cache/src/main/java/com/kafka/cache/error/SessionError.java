package com.kafka.cache.error;

public class SessionError extends RuntimeException{

    private String msg;
    private String caused;

    public SessionError() {
    }

    public SessionError(String msg, String caused) {
        this.msg = msg;
        this.caused = caused;
    }
}
