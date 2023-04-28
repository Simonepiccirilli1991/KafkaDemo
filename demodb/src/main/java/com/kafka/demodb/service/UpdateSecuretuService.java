package com.kafka.demodb.service;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.exception.GenericError;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.fragment.CheckOtpvService;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

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

        //checko se counter e maggiore = a 3 in caso blocco senza fare check, ha gai superato limite
        // non torno nulla che possa far enumeration
        var userCounter = secCounterCrudService.getCounter(userKey);
        if(ObjectUtils.isEmpty(userCounter) || userCounter.getEmailCounter() >= 3)
            throw new GenericError();

        var userSec = userSecCrudService.getUserSec(userKey);

        if(ObjectUtils.isEmpty(userSec))
            throw new CustomError("User_not_found","user not found with this userKey", LocalDateTime.now(), HttpStatus.CONFLICT);

        var check = checkOtpvService.checkOtp(trxId,userKey,opt);
        // se otp e corretto updato certifica
        if(check){
            userSecCrudService.updateEmailCert(userKey);
            return new BaseDbResponse("OK-00 - Otp Verified");
        }
        else{
            secCounterCrudService.resetCounterEmail(userKey);
            throw new CustomError("Invalid_Otp","InvalidOtp", LocalDateTime.now(), HttpStatus.FORBIDDEN);
        }
    }

    //changePsw

    //retrivePsw
}
