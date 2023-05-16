package com.kafka.orc.service;

import com.kafka.orc.constants.Action;
import com.kafka.orc.fragment.usersic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    UserService userService;

    public StatusResponse checkStatus(String userkey){


        var status = userService.statusUserSic(userkey);

        // utente non registrato
        if(!status.getRegistered())
            return new StatusResponse(Action.REGISTER,"not registered");
        else if(status.getCertified())
            return new StatusResponse(Action.CONSET,"registed and certified");
        else
            return new StatusResponse(Action.CERTIFY,"not certified");

        // TODO: easy solo su classico, addare logica poi intersecando bank user

    }

    public record StatusResponse(Action action,String status){}
}
