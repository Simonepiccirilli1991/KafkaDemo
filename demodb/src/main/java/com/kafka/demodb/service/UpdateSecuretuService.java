package com.kafka.demodb.service;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.exception.GenericError;
import com.kafka.demodb.model.request.ChangePswRequest;
import com.kafka.demodb.model.request.RetrivePswRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.fragment.CheckOtpvService;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserCrudService;
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
    private final UserCrudService userCrudService;

    public UpdateSecuretuService(SecCounterCrudService secCounterCrudService, UserSecCrudService userSecCrudService, CheckOtpvService checkOtpvService, UserCrudService userCrudService) {
        this.secCounterCrudService = secCounterCrudService;
        this.userSecCrudService = userSecCrudService;
        this.checkOtpvService = checkOtpvService;
        this.userCrudService = userCrudService;
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
            return new BaseDbResponse("KO-00","InvalidOtp", "Invalid_Otp");
        }
    }

    //changePsw
    public BaseDbResponse changePsw(ChangePswRequest request){

        var userAcc = userCrudService.getUser(request.getEmail(),request.getUserKey());

        //se user non esiste lancio generic per non fare enumeration
        if(ObjectUtils.isEmpty(userAcc.getUser()))
            throw new GenericError();

        if(request.getPsw().equals(userAcc.getUser().getPsw()))
            throw new CustomError("Invalid_Psw","New psw can be same as old",LocalDateTime.now(),HttpStatus.CONFLICT);

        var check = checkOtpvService.checkOtp(request.getTrxId(),request.getTrxId(),request.getOtp());
        // se otp e corretto updato certifica
        if(check){
          var resp =   userCrudService.updateUserPsw(request.getUserKey(),request.getPsw());
            return new BaseDbResponse("OK-00 - Psw changed");
        }
        else{
            secCounterCrudService.updateCounterPsw(request.getUserKey());
            return new BaseDbResponse("KO-00","InvalidOtp", "Invalid_Otp");
        }
    }

    //retrivePsw
    public BaseDbResponse retrivePsw(RetrivePswRequest request){

        var userAcc = userCrudService.getUser(request.getEmail(),request.getUserKey());

        //se user non esiste lancio generic per non fare enumeration
        if(ObjectUtils.isEmpty(userAcc.getUser()))
            throw new GenericError();

        var check = checkOtpvService.checkOtp(request.getTrxId(),request.getUserKey(),request.getOtp());
        // se otp e corretto updato certifica
        if(check){
            return new BaseDbResponse("OK-00",userAcc.getUser().getPsw());
        }
        else{
            secCounterCrudService.updateCounterMail(request.getUserKey());
            return new BaseDbResponse("KO-00","InvalidOtp", "Invalid_Otp");
        }
    }
}
