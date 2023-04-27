package com.kafka.demodb.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomError extends RuntimeException {

    private String errTp;
    private String errMsg;
    private LocalDateTime time;
    private HttpStatus status;


}
