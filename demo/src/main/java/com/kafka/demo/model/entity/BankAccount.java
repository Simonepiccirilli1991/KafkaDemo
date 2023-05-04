package com.kafka.demo.model.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name ="bank_account")
public class BankAccount {

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
