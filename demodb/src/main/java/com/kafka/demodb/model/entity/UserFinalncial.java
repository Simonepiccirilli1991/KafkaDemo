package com.kafka.demodb.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_financial",uniqueConstraints=
@UniqueConstraint(columnNames={"userKey", "username", "email"}))
public class UserFinalncial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userKey;
    private String username;
    private String email;
    private Double accountBalance;

}
