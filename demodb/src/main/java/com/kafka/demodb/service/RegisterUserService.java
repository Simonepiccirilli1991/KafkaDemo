package com.kafka.demodb.service;

import com.kafka.demodb.model.entity.User;
import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RegisterUserService {

    @Autowired
    UserCrudService userService;


    public BaseDbResponse registerUser(UserRequest request){

        if(ObjectUtils.isEmpty(request.getPsw()) || ObjectUtils.isEmpty(request.getEmail())
        || ObjectUtils.isEmpty(request.getUsername()))
            return new BaseDbResponse("ERKO-03", "Missing parameters on request", "Invalid_Request");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPsw(request.getPsw());
        user.setUserKey(String.valueOf(request.getUsername().hashCode()+request.getEmail().hashCode()));

        return userService.insertUser(user);

    }
}
