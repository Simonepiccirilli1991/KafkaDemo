package com.kafka.cache.service;

import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.SicSession;
import com.kafka.cache.model.request.SessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    SessionCacheService sessionService;


    public SessionCreateResp createSession(SessionRequest request){

        //TODO: creare metodo che fa get all e controllare che non sia gia stata creata.

        SicSession session = new SicSession();
        session.setUserKey(request.getUserKey());
        session.setScope(request.getScope());
        session.setUpdate(false);
        session.setCreationDate(LocalDateTime.now());

        var idSess= UUID.randomUUID().toString();

        try {
            sessionService.insert(idSess, session);
        }catch (Exception e){
            throw new SessionError("Error on creating session", e.getMessage());
        }
        return new SessionCreateResp(idSess,true);
    }

    public SessionUpdate updateSession(String sessionId){

        var session = sessionService.get(sessionId);

        if(ObjectUtils.isEmpty(session))
            throw new SessionError("Error on updating session", "Session don't exist");

        session.setUpdate(true);
        session.setUpdateTime(LocalDateTime.now());

        if(session.getScope().equals("l1"))
            session.setScope("l2");
        try {
            sessionService.put(sessionId, session);
        }catch (Exception e){
            throw new SessionError("Error on updating session", e.getMessage());
        }

        return new SessionUpdate(sessionId, true);
    }

    public GetSession getSession(String sessionId){

        var session = sessionService.get(sessionId);

        if(ObjectUtils.isEmpty(session))
            throw new SessionError("Error on get session", "Session don't exist");

        return new GetSession(session);
    }

    public record SessionCreateResp(String sessionId, Boolean created){}
    public record SessionUpdate(String sessionId,Boolean updated){}
    public record GetSession(SicSession session){}
}