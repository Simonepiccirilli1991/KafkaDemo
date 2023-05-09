package com.kafka.orc.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private String cause;
    private String msg;
    private LocalDateTime time;
}
