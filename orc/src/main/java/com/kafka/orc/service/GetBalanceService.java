package com.kafka.orc.service;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.bankacc.BankUserService;
import com.kafka.orc.model.fragment.GetBalanceAccResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GetBalanceService {

    //TODO: creare servizio che vede stato balance acc.
    //TODO: creare servizio che aumenta balance acc.
    //TODO: creare servizio di pagamenti.

    @Autowired
    BankUserService bankUserService;

    public GetBalanceAccResponse getBalanceAcc(String userKey){

        var resp = bankUserService.getBalanceAcc(userKey);

        if(ObjectUtils.isEmpty(resp.getAmountAviable()))
            throw new OrcError("balance amount is empty", "PI_MS_5000: Generic error","UserBankBalanceKO-03");

        return resp;
    }
}
