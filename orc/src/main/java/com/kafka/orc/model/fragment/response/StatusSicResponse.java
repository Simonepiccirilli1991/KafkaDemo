package com.kafka.orc.model.fragment.response;

import lombok.Data;

@Data
public class StatusSicResponse {

    private String response;
    private Boolean registered;
    private Boolean certified;
}
