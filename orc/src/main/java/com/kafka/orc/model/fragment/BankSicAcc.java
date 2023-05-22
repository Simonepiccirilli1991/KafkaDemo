package com.kafka.orc.model.fragment;

import lombok.Data;

@Data
public class BankSicAcc {

   private String userKey;
   private int accNumber;
   private boolean certified;
}
