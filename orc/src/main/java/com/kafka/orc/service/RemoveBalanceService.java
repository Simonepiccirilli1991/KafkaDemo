package com.kafka.orc.service;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.bankacc.BankBalanceService;
import com.kafka.orc.fragment.session.UserSessionService;
import com.kafka.orc.model.fragment.request.BalanceRequest;
import com.kafka.orc.model.fragment.response.AmountBankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RemoveBalanceService {

    @Autowired
    BankBalanceService balanceService;
    @Autowired
    UserSessionService userSessionService;

    public AmountBankResponse removeBalance(BalanceRequest request, HttpHeaders headers){

        if(ObjectUtils.isEmpty(request.getAmount()) || ObjectUtils.isEmpty(request.getUserKey()))
            throw new OrcError("Invalid_request", "missing parameters on request", "Invalid_Request");

        var sessionId = headers.getFirst("sessionId");
        //TODO: add check session valid first

        if(ObjectUtils.isEmpty(sessionId))
            throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

        var session = userSessionService.getSession(sessionId);

        if(!session.getSession().getScope().equals("l2"))
            throw new OrcError("Session low lever","session is not in scope l2","Invalid_Session");

        var resp = balanceService.removeBalanceAcc(request.getUserKey(), request.getAmount());

        return resp;
    }
}
