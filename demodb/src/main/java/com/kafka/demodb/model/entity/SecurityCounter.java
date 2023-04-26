package com.kafka.demodb.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sec_counter",uniqueConstraints=
@UniqueConstraint(columnNames={"userKey"}))
public class SecurityCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userKey;
    private long pswCounter;
    private long emailCounter;

    private LocalDateTime lastPswBlock;
    private LocalDateTime lastEmailBlock;
}
