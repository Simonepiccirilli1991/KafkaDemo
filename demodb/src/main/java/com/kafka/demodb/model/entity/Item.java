package com.kafka.demodb.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item",uniqueConstraints=
@UniqueConstraint(columnNames={"name"}))
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String desctiption;
    private long quantity;
    private Double price;
}
