package com.kafka.orc.fragment.usersic;

import com.kafka.orc.client.UserWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.GetUserByUsernResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    @Autowired
    UserWebClient userWebClient;

    public BaseDbResponse registerUser(UserSicRequest request){

        var resp = userWebClient.registerSic(request);
        if(!resp.getResult().equals("OK-00"))
            throw new OrcError(resp.getErrType(),resp.getErrMsg(),"UserRegKO-02");

        return resp;
    }

    public BaseDbResponse checkPinUser(UserSicRequest request){

        var resp = userWebClient.checkPinUser(request);
        if(!resp.getResult().equals("OK-00"))
            throw new OrcError(resp.getErrType(),resp.getErrMsg(),"UserCheckKO-02");

        return resp;
    }

    public StatusSicResponse statusUserSic(String userKey){

        UserSicRequest request = new UserSicRequest();
        request.setUserKey(userKey);

        StatusSicResponse resp = userWebClient.statusSic(request);
        if(!resp.getResponse().equals("OK-00"))
            throw new OrcError("Error_StatusUser","Erro on get status user","UserCheckStatusKO-02");

        return resp;
    }

    public GetUserByUsernResponse getUserByUsername(String username){

        var resp = userWebClient.getUserByUsername(username);
        if(ObjectUtils.isEmpty(resp) || ObjectUtils.isEmpty(resp.getUserKey()))
            throw new OrcError("User not found","Username provided are not valid, not user associated","UserGetByKO-02");

        return resp;
    }
}
