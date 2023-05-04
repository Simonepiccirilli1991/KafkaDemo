package com.kafka.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_acc_sic")
public class BankAccSic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userKey;
    private int accNumber;
    private boolean certified;
}
