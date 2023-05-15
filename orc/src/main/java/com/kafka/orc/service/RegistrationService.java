package com.kafka.orc.service;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.fragment.bankacc.BankUserService;
import com.kafka.orc.fragment.usersic.UserService;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.request.RegisterRequest;
import com.kafka.orc.model.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RegistrationService {

    @Autowired
    UserService userService;
    @Autowired
    BankUserService bankUserService;


    public RegisterResponse registerUT(RegisterRequest request){


        var userKey = (ObjectUtils.isEmpty(request.getUserKey())) ? "userDefault" : request.getUserKey();
        if(!userKey.equals("userDefault")) {
            var us = userService.statusUserSic(userKey);
            if(us.getRegistered()) // la status se user non esiste torna oggetto vuoto
                throw new OrcError("Already Exist","User already exist","RegisterKO-01");
        }
        UserSicRequest iReq = new UserSicRequest();
        iReq.setPsw(request.getPsw());
        iReq.setEmail(request.getEmail());
        iReq.setUsername(request.getUsername());

        var resp = userService.registerUser(iReq);

        var finalUserKet = resp.getUserKey();
        // adesso dovrei creare la parte di bank , poi logga e certifica

        UserAccRequest eRequest = new UserAccRequest();
        eRequest.setEmail(request.getEmail());
        eRequest.setNome(request.getUsername());
        eRequest.setCognome(request.getCognome());
        eRequest.setUserKey(finalUserKet);

        var iResp = bankUserService.registerUser(eRequest);


        return new RegisterResponse(finalUserKet, iResp.getAccNumber());
    }
}
