package com.kafka.demodb.service;

import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.stereotype.Service;

@Service
public class UpdateSecuretuService {

    private final SecCounterCrudService secCounterCrudService;
    private final UserSecCrudService userSecCrudService;

    public UpdateSecuretuService(SecCounterCrudService secCounterCrudService, UserSecCrudService userSecCrudService) {
        this.secCounterCrudService = secCounterCrudService;
        this.userSecCrudService = userSecCrudService;
    }

    //TODO: tutti i servizi qui lavorano con otp
    // in caso di otp errato/ invio otp aumentare counterMail
    //certifiMail
    public BaseDbResponse certifyMailUser(String userKey, String email, String opt){

        //TODO: integrare con servizio di checkOtp
        return new BaseDbResponse();
    }
    //changePsw

    //retrivePsw
}
