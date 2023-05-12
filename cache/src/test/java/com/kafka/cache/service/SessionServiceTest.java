package com.kafka.cache.service;

import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.request.SessionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
