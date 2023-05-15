package com.kafka.orc.service;

import com.kafka.orc.constants.Action;
import com.kafka.orc.fragment.session.UserSessionService;
import com.kafka.orc.fragment.usersic.UserService;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.request.LoginRequest;
import com.kafka.orc.model.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginService {

    @Autowired
    UserService userService;
    @Autowired
    UserSessionService userSessionService;

    public LoginPreResponse login(LoginRequest request){

        // puo loggare con tutti e 2, se logga co username famo na chiamata in piu
        //caso userKey
        if(!ObjectUtils.isEmpty(request.getUserKey())){

            var id = request.getUserKey();

            UserSicRequest iRequest = new UserSicRequest();
            iRequest.setPsw(request.getPsw());
            iRequest.setUserKey(request.getUserKey());

            userService.checkPinUser(iRequest);

            // chiamata per creazione sessione.
            var session = userSessionService.createSession(id).sessionId();
            // chiamata a status per vedere che deve fare. certify o checkOtp
            var status = userService.statusUserSic(id);
            //certificato sendOtp
            if(status.getCertified())
                return new LoginPreResponse(id, Action.SENDOTP,session);
            else
                return new LoginPreResponse(id,Action.CERTIFY,session);
        //caso username
        }else{
            var userKey = userService.getUserByUsername(request.getUsername()).getUserKey();

            UserSicRequest iRequest = new UserSicRequest();
            iRequest.setUserKey(userKey);
            iRequest.setPsw(request.getPsw());

            userService.checkPinUser(iRequest);

            // chiamata per creazione sessione.
            var session = userSessionService.createSession(userKey).sessionId();

            // chiamata a status per vedere che deve fare. certify o checkOtp
            var status = userService.statusUserSic(userKey);
            //certificato sendOtp
            if(status.getCertified())
                return new LoginPreResponse(userKey,Action.SENDOTP,session);
            else
                return new LoginPreResponse(userKey,Action.CERTIFY,session);
        }
    }

    public record LoginPreResponse (String userKey, Action action,String sessionId){}
}
