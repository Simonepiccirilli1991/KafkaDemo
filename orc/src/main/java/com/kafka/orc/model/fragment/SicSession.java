package com.kafka.orc.model.fragment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SicSession {

    private String userKey;
    private String scope;
    private LocalDateTime creationDate;
    private Boolean update;
    private LocalDateTime updateTime;
}
