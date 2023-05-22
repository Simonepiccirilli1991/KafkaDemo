package com.kafka.orc.service;

import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.bankacc.BankUserService;
import com.kafka.orc.fragment.session.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankAccStatusService {

    @Autowired
    UserSessionService userSessionService;
    @Autowired
    BankUserService bankUserService;

    //API invocata dopo la consent per vedere se il tizio deve ancora validare la bankAcc.

    public Action statusPostLoginBank(String userKey, HttpHeaders header){


        // prima di fare la status checko che il tizio abbia la sessione esistente e in l2, altrimenti piripicchio
        var sessionId = header.getFirst("sessionId");
        if(ObjectUtils.isEmpty(sessionId))
            throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

        var session = userSessionService.getSession(sessionId);

        if(!"l2".equals(session.getSession().getScope()))
            throw new OrcError("Invalid_Session","invalid session l2 scope  is missing","Invalid_Session");

        // se gia certificato torniamo PERFORM, se no ACCCERTY

        var statusBank = bankUserService.statusBankAcc(userKey);

        if(statusBank.getBankAccSicInfo().isCertified())
            return Action.PERFORM;
        else
            return Action.BANKCERTIFY; // bank certify e send otp per bankAcc bankcertified e la check
    }
}
