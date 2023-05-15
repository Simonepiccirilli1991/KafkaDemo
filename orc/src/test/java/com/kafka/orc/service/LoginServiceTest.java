package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.constants.Action;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.CreateSessionResponse;
import com.kafka.orc.model.fragment.response.GetUserByUsernResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import com.kafka.orc.model.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest extends BaseOrcTest {

    @Test
    void loginServiceUserKeyTestOK(){

        LoginRequest request = new LoginRequest();
        request.setUserKey("userkey");
        request.setPsw("psw");

        BaseDbResponse dbResp = new BaseDbResponse();
        dbResp.setResult("OK-00");
        Mockito.when(userWebClient.checkPinUser(Mockito.any())).thenReturn(dbResp);

        CreateSessionResponse sessResp = new CreateSessionResponse("1234", true);

        Mockito.when(cacheWebClient.createSession(Mockito.any())).thenReturn(sessResp);

        StatusSicResponse statusResp = new StatusSicResponse();
        statusResp.setRegistered(true);
        statusResp.setCertified(false);
        statusResp.setResponse("OK-00");

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(statusResp);

        var response = loginService.login(request);

        assert response.action().equals(Action.CERTIFY);
        assert response.sessionId().equals("1234");
    }

    @Test
    void loginServiceUsernameTestOK(){

        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPsw("psw");

        BaseDbResponse dbResp = new BaseDbResponse();
        dbResp.setResult("OK-00");

        GetUserByUsernResponse getUserResp = new GetUserByUsernResponse();
        getUserResp.setUsername("username");
        getUserResp.setUserKey("userkey");
        getUserResp.setEmail("meail");

        Mockito.when(userWebClient.getUserByUsername(Mockito.any())).thenReturn(getUserResp);
        Mockito.when(userWebClient.checkPinUser(Mockito.any())).thenReturn(dbResp);

        CreateSessionResponse sessResp = new CreateSessionResponse("1234", true);

        Mockito.when(cacheWebClient.createSession(Mockito.any())).thenReturn(sessResp);

        StatusSicResponse statusResp = new StatusSicResponse();
        statusResp.setRegistered(true);
        statusResp.setCertified(false);
        statusResp.setResponse("OK-00");

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(statusResp);

        var response = loginService.login(request);

        assert response.action().equals(Action.CERTIFY);
        assert response.sessionId().equals("1234");
    }
}
