package com.kafka.demoorc.model.fragment;

import lombok.Data;

@Data
public class Item {

    private long id;
    private String name;
    private String desctiption;
    private long quantity;
    private Double price;
}
