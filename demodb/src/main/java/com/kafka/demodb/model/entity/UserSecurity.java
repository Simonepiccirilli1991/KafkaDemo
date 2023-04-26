package com.kafka.demodb.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_security",uniqueConstraints=
@UniqueConstraint(columnNames={"userKey"}))
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userKey;

    private String lastPsw;
    private Boolean emailCertified;


}
