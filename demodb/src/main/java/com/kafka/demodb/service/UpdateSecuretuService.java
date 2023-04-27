package com.kafka.demodb.service;

import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.fragment.CheckOtpvService;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.stereotype.Service;

@Service
public class UpdateSecuretuService {

    private final SecCounterCrudService secCounterCrudService;
    private final UserSecCrudService userSecCrudService;
    private final CheckOtpvService checkOtpvService;

    public UpdateSecuretuService(SecCounterCrudService secCounterCrudService, UserSecCrudService userSecCrudService, CheckOtpvService checkOtpvService) {
        this.secCounterCrudService = secCounterCrudService;
        this.userSecCrudService = userSecCrudService;
        this.checkOtpvService = checkOtpvService;
    }

    //TODO: tutti i servizi qui lavorano con otp
    // in caso di otp errato/ invio otp aumentare counterMail
    //certifiMail
    public BaseDbResponse certifyMailUser(String userKey, String email, String opt,String trxId){

        //TODO: manca fragment e client che chiamano checkotpv, il microservizio gai esiste
        //TODO: integrare con servizio di checkOtp, il microservices e stato creato, va solo integrato
        return new BaseDbResponse();
    }
    //changePsw

    //retrivePsw
}
