package com.kafka.orc.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrcError extends  RuntimeException{


    private String caused;
    private String msg;
    private String errId;


}
