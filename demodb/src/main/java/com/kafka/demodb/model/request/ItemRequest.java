package com.kafka.demodb.model.request;

import lombok.Data;

@Data
public class ItemRequest {

    private String name;
    private String desctiption;
    private long quantity;
    private Double price;
}
