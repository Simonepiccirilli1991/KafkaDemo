package com.kafka.demoorc.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SagaOrcError {

    private String msg;
    private String cause;
    private LocalDateTime date;
}
