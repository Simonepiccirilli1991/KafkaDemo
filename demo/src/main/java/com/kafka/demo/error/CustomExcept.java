package com.kafka.demo.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomExcept extends RuntimeException{

    private String cause;
    private String msg;
    private HttpStatus status;

    public CustomExcept(String cause, String msg, HttpStatus status) {
        this.cause = cause;
        this.msg = msg;
        this.status = status;
    }
}
