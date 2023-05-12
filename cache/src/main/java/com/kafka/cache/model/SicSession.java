package com.kafka.cache.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SicSession implements Serializable {

    private String userKey;
    private String scope;
    private LocalDateTime creationDate;
    private Boolean update;
    private LocalDateTime updateTime;
}
