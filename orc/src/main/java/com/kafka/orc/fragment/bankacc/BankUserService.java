package com.kafka.orc.fragment.bankacc;

import com.kafka.orc.client.BankAccWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankUserService {

    @Autowired
    BankAccWebClient bankAccWebClient;

    public BaseBankResponse registerUser(UserAccRequest request){

        var resp = bankAccWebClient.registerBankUser(request);
        if(ObjectUtils.isEmpty(resp.getIsError()) || resp.getIsError())
            throw new OrcError(resp.getResult(),resp.getMsg(),"UserBankRegKO-02");

        return resp;
    }
}
