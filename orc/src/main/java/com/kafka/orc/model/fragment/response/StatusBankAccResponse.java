package com.kafka.orc.model.fragment.response;

import com.kafka.orc.model.fragment.BankAcc;
import com.kafka.orc.model.fragment.BankSicAcc;
import lombok.Data;

@Data
public class StatusBankAccResponse {

    private Boolean notPresent;
    private BankAcc bankAccInfo;
    private BankSicAcc bankAccSicInfo;
}
