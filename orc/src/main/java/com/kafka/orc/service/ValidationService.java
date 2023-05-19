package com.kafka.orc.service;

import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.otpv.OtpvService;
import com.kafka.orc.fragment.session.UserSessionService;
import com.kafka.orc.fragment.usersic.UserSicService;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.request.ValidationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class ValidationService {


    @Autowired
    UserSicService userSicService;
    @Autowired
    UserSessionService userSessionService;
    @Autowired
    OtpvService otpvService;

    public Action validationService(ValidationRequest request, HttpHeaders headers){

        if(ObjectUtils.isEmpty(request.getAction()) || !request.getAction().equals(Action.CERTIFY) && !request.getAction().equals(Action.CHECKOTP))
            throw new OrcError("Invalid_Action","Invalid action provided","InvalidActionVerifyKO-01");


        if(Action.CERTIFY.equals(request.getAction())){


            // certifico bassando da iwdb che chiama lui la checkOtp
            CertifyMailSicRequest certRequest = new CertifyMailSicRequest();
            certRequest.setOtp(request.getOtp());
            certRequest.setTrxId(request.getTrxId());
            certRequest.setUserKey(request.getUserKey());
            userSicService.certifyUserSic(certRequest);
            //updato session
            var sessionId = headers.getFirst("sessionId");

            if(ObjectUtils.isEmpty(sessionId))
                throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

            userSessionService.updateSession(sessionId);

        }
        else{
            var sessionId = headers.getFirst("sessionId");

            if(ObjectUtils.isEmpty(sessionId))
                throw new OrcError("Session_Missing","SessionId is missing","Invalid_Session");

            // chiamata direttamnte a otpv per check
            otpvService.checkOtpv(request.getUserKey(),request.getTrxId(), request.getOtp());
            // updato session
            userSessionService.updateSession(sessionId);

        }

        // che sia certify o normale checkOtp torniamo sempre conset al momento per farlo andare in postLogin
        return Action.CONSET;
    }
}
