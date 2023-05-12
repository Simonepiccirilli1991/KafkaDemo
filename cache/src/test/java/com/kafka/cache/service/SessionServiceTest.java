package com.kafka.cache.service;

import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.SicSession;
import com.kafka.cache.model.request.SessionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SessionServiceTest {

    //TODO: fare junit per sessionService
    @Autowired
    SessionService sessionService;
    @Autowired
    SessionCacheService sessionCacheService;


    @Test
    void createSessionTestOK(){

        SessionRequest request = new SessionRequest();
        request.setScope("l1");
        request.setUserKey("userKey");

        var resp = sessionService.createSession(request);

        assert resp != null;

        assert sessionCacheService.get(resp.sessionId()) != null;
    }

    @Test
    void createSessionTestKO(){

        SessionError ex = assertThrows(SessionError.class, () -> {
            sessionService.createSession(new SessionRequest());
        });

        assert ex.getCaused().equals("Generic_Error");
        assert ex.getMsg().equals("Error on creating session");
    }

    @Test
    void updateSessionTestOK(){

        SicSession session = new SicSession();
        session.setCreationDate(LocalDateTime.now());
        session.setScope("l1");
        session.setUpdate(false);
        session.setUserKey("userKey");

        sessionCacheService.insert("1234",session);

        var resp = sessionService.updateSession("1234");

        assert resp.updated() == true;

        var sess = sessionCacheService.get("1234");

        assert sess.getUpdate() == true;
        assert sess.getScope().equals("l2");
    }

    @Test
    void updateSessionTestKO(){

        SessionError ex = assertThrows(SessionError.class, () -> {
            sessionService.updateSession("123456");
        });

        assert ex.getCaused().equals("Session don't exist");
        assert ex.getMsg().equals("Error on updating session");
    }

    @Test
    void getSessionTestOK(){

        SicSession session = new SicSession();
        session.setCreationDate(LocalDateTime.now());
        session.setScope("l1");
        session.setUpdate(false);
        session.setUserKey("userKey");

        sessionCacheService.insert("1234",session);

        var resp = sessionService.getSession("1234");

        assert resp != null;
        assert resp.session().getScope().equals("l1");
        assert resp.session().getUserKey().equals("userKey");
    }

    @Test
    void getSessionTestKO(){

        SessionError ex = assertThrows(SessionError.class, () -> {
            sessionService.getSession("1234");
        });

        assert ex.getCaused().equals("Session don't exist");
        assert ex.getMsg().equals("Error on get session");

    }

    @Test
    void checkValidSessionTestOK(){

        SicSession session = new SicSession();
        session.setCreationDate(LocalDateTime.now());
        session.setScope("l1");
        session.setUpdate(false);
        session.setUserKey("userKey");

        sessionCacheService.insert("12347",session);

        var resp = sessionService.checkSessionValid("12347");

        assert resp != null;
        assert resp == true;
    }

    @Test
    void checkValidSessionTestKO(){

        SessionError ex = assertThrows(SessionError.class, () -> {
            sessionService.checkSessionValid("12345");
        });

        assert ex.getCaused().equals("Session don't exist");
        assert ex.getMsg().equals("Error on get on check session");
    }
}
