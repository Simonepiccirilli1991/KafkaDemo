package com.kafka.demo.model.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userKey;
    private int accNumber;
    private String email;
    private String nome;
    private String cognome;
    private String type;
    private LocalDateTime dataApertura;
    private Double amountAviable;
}
