package com.kafka.orc.fragment.session;

import com.kafka.orc.client.CacheWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.SessionRequest;
import com.kafka.orc.model.fragment.response.CreateSessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserSessionService {

    @Autowired
    CacheWebClient cacheWebClient;


    public CreateSessionResponse createSession(String userKey){

        SessionRequest request = new SessionRequest();
        request.setUserKey(userKey);
        request.setScope("l1");

        var resp = cacheWebClient.createSession(request);

        if(ObjectUtils.isEmpty(resp) || ObjectUtils.isEmpty(resp.sessionId()) || ObjectUtils.isEmpty(resp.result()))
            throw new OrcError("CreateSess_Error","Create session response is empty","CreateSessKO-02");

        return  resp;
    }

    public Boolean checkSession(String sessionID){

        var resp = cacheWebClient.checkValidSession(sessionID);
        if(ObjectUtils.isEmpty(resp))
            throw new OrcError("CheckSess_Error","Check session response is empty","CheckSessKO-02");

        return resp;
    }

    public void updateSession(String userKey){

        var resp = cacheWebClient.updateSession(userKey);
        if(ObjectUtils.isEmpty(resp) || !resp.updated())
            throw new OrcError("UpdateSess_Error","Update session response is empty","UpdateSessKO-02");

    }
}
