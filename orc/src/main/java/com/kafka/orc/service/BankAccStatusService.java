package com.kafka.orc.service;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.session.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankAccStatusService {

    @Autowired
    UserSessionService userSessionService;

    //API invocata dopo la consent per vedere se il tizio deve ancora validare la bankAcc.

    public void statusPostLoginBank(String userKey, HttpHeaders header){


        // prima di fare la status checko che il tizio abbia la sessione esistente e in l2, altrimenti piripicchio
        var sessionId = header.getFirst("sessionId");
        if(ObjectUtils.isEmpty(sessionId))
            throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

        var session = userSessionService.getSession(sessionId);

        if(!"l2".equals(session.getSession().getScope()))
            throw new OrcError("Invalid_Session","invalid session l2 scope  is missing","Invalid_Session");

        //TODO: creare la status BankAcc nel client e tornare action in base a cosa deve fare
        // se gia certificato torniamo PERFORM, se no ACCCERTY

    }
}
