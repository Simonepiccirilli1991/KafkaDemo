package com.kafka.orc.service;

import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.otpv.OtpvService;
import com.kafka.orc.fragment.session.UserSessionService;
import com.kafka.orc.model.request.GenerateOtpvRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GenerateOtpService {

    @Autowired
    OtpvService otpvService;
    @Autowired
    UserSessionService userSessionService;

    public String generateOtpv(GenerateOtpvRequest request, HttpHeaders header){

        if(ObjectUtils.isEmpty(request.getAction()) || !request.getAction().equals(Action.SENDOTP)
        && !request.getAction().equals(Action.BANKCERTIFY))
            throw new OrcError("Invalid_Action","Invalid action provided","InvalidActionGenerateKO-01");

        //TODO: vedere se implementare logica di ritorno action, al momento la gestiamo solo in entrata non in uscita qui
        var sessionId = header.getFirst("sessionId");

        if(ObjectUtils.isEmpty(sessionId))
            throw new OrcError("Invalid_Session","Invalid sessionId provided","InvalidSessionKO-01");

        //checko sessione
        var sessionValid = userSessionService.checkSession(sessionId);

        if(!sessionValid)
            throw new OrcError("Invalid_Session","Session is expired","InvalidSessionKO-02");

        var resp = otpvService.generateOtp(request.getUserKey());

        return resp.getTrxId();
    }
}
