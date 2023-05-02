package com.kafka.demodb.service;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class StatusService {

    private final GetUserService getUserService;
    private final UserSecCrudService userSecCrudService;

    public StatusService(GetUserService getUserService, UserSecCrudService userSecCrudService) {
        this.getUserService = getUserService;
        this.userSecCrudService = userSecCrudService;
    }

    public StatusResponse getUserStatus(String email, String userKey){

        if(ObjectUtils.isEmpty(email) && ObjectUtils.isEmpty(userKey))
            throw new CustomError("Invalid_Request", "Invalid requst, missing parameter", LocalDateTime.now(), HttpStatus.BAD_REQUEST);

        var user = getUserService.getUser(email,userKey);

        if(ObjectUtils.isEmpty(user.getUser()))
            return new StatusResponse("OK-00",false,false);

        var userK = (!ObjectUtils.isEmpty(userKey)) ? userKey : user.getUserKey();

        var userSec = userSecCrudService.getUserSec(userK);

        if(ObjectUtils.isEmpty(userSec) || !userSec.getEmailCertified())
            return new StatusResponse("OK-00",true,false);
        else{
            return new StatusResponse("OK-00",true,true);
        }
    }

    public record StatusResponse(String response,Boolean registered,Boolean certified){}
}
