package com.kafka.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_acc_sic")
public class BankAccSic {

    private int id;
    private String userKey;
    private int accNumber;
    private boolean certified;
}
