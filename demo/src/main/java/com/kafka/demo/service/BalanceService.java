package com.kafka.demo.service;

import com.kafka.demo.service.internal.BankAccCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BalanceService {


    @Autowired
    BankAccCrudService bankAccCrudService;

    public BankAccCrudService.GetBankAccSummaryFilter getBalanceAcc(String userKey){

        //gestione errore e gia interna al service fragment
        var resp = bankAccCrudService.getBankAccByUserKey(userKey);

        return resp;
    }
}
