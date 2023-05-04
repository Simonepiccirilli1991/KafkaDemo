package com.kafka.demo.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorExc {

    private String cause;
    private String msg;
    private LocalDateTime data;
}
