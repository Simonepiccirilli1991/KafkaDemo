package com.kafka.orc.fragment.bankacc;

import com.kafka.orc.client.BankAccWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.response.AmountBankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankBalanceService {

    @Autowired
    BankAccWebClient bankAccWebClient;


    public AmountBankResponse addBalanceAcc(String userKey, Double amount){

        var resp = bankAccWebClient.addORemoveBalance(userKey,amount,false);

        if(ObjectUtils.isEmpty(resp) || !resp.getResult().equals("200-OK"))
            throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankBalanceKO-02");

        return resp;
    }

    public AmountBankResponse removeBalanceAcc(String userKey, Double amount){

        var resp = bankAccWebClient.addORemoveBalance(userKey,amount,true);

        if(ObjectUtils.isEmpty(resp) || !resp.getResult().equals("200-OK"))
            throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankBalanceKO-02");

        return resp;
    }

}
