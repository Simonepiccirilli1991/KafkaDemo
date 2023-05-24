package com.kafka.orc.service;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.payment.PaymentBankService;
import com.kafka.orc.fragment.session.UserSessionService;
import com.kafka.orc.model.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PaymentService {

    @Autowired
    UserSessionService userSessionService;
    @Autowired
    PaymentBankService paymentService;

    public Boolean payment(PaymentRequest request, HttpHeaders headers){

        //TODO: il servizio si occupa solo di ingaggiare il payment service orc, il payment
        // orc gestisce lui tutto il saga pattern

        // controllo che sessione esista e sia valida
        var sessionId = headers.getFirst("sessionId");

        if(ObjectUtils.isEmpty(sessionId))
            throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

        var session = userSessionService.getSession(sessionId);

        if(!session.getSession().getScope().equals("l2"))
            throw new OrcError("Session low lever","session is not in scope l2","Invalid_Session");


        var pay = paymentService.payment(request);

        return pay;

    }

}
