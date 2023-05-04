package com.kafka.demo.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomExcept extends RuntimeException{

    private String caused;
    private String msg;
    private HttpStatus status;

    public CustomExcept(String caused, String msg, HttpStatus status) {
        this.caused = caused;
        this.msg = msg;
        this.status = status;
    }
}
