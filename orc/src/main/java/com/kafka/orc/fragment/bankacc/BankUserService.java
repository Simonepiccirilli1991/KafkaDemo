package com.kafka.orc.fragment.bankacc;

import com.kafka.orc.client.BankAccWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.StatusBankAccResponse;
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

    public StatusBankAccResponse statusBankAcc(String userKey){

        var response = bankAccWebClient.getStatus(userKey);

        if(ObjectUtils.isEmpty(response) || response.getNotPresent())
            throw new OrcError("BankAcc_Missing","User bank account are not present","StatusBankRegKO-02");

        return response;
    }

    public void certifyBankAcc(String userKey){

        var resp = bankAccWebClient.certifyBankAcc(userKey);

        if(ObjectUtils.isEmpty(resp) || resp.getIsError())
            throw new OrcError(resp.getResult(),resp.getMsg(),"UserBankCertKO-02");
    }
}
