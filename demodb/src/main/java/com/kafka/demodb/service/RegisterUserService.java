package com.kafka.demodb.service;

import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RegisterUserService {

    @Autowired
    UserCrudService userService;
    @Autowired
    UserSecCrudService userSecCrudService;
    @Autowired
    SecCounterCrudService secCounterCrudService;


    public BaseDbResponse registerUser(UserRequest request){

        if(ObjectUtils.isEmpty(request.getPsw()) || ObjectUtils.isEmpty(request.getEmail())
        || ObjectUtils.isEmpty(request.getUsername()))
            return new BaseDbResponse("ERKO-03", "Missing parameters on request", "Invalid_Request");

        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPsw(request.getPsw());
        user.setUserKey(String.valueOf(request.getUsername().hashCode()+request.getEmail().hashCode()));

        var response =  userService.insertUser(user);
        userSecCrudService.createUserSec(user.getUserKey(),user.getPsw());
        secCounterCrudService.createSecuretyCounter(user.getUserKey());

        return response;
    }
}
