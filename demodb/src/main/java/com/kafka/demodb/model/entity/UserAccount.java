package com.kafka.demodb.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_account",uniqueConstraints=
@UniqueConstraint(columnNames={"userKey", "username", "email"}))
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String email;
    private String psw;
    private String userKey;
}
