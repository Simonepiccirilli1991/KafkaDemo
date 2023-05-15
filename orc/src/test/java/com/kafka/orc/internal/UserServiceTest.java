package com.kafka.orc.internal;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.GetUserByUsernResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import com.kafka.orc.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest extends BaseOrcTest {

    @Test
    void registerUserTestOK(){

        UserSicRequest request = new UserSicRequest();
        request.setUsername("username");
        request.setPsw("psw");
        request.setEmail("mail");

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        Mockito.when(userWebClient.registerSic(Mockito.any())).thenReturn(dbRespo);

        var resp = userService.registerUser(request);

        assert resp.getUserKey().equals("userKey1");
    }

    @Test
    void registerUsetTestKO(){

        UserSicRequest request = new UserSicRequest();
        request.setUsername("username");
        request.setPsw("psw");
        request.setEmail("mail");

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("KO-00");
        dbRespo.setUserKey("userKey1");
        dbRespo.setErrMsg("Generic Error");
        dbRespo.setErrType("Internal Error");

        Mockito.when(userWebClient.registerSic(Mockito.any())).thenReturn(dbRespo);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userService.registerUser(request);
        });

        assert ex.getCaused().equals("Internal Error");
        assert ex.getErrId().equals("UserRegKO-02");
        assert ex.getMsg().equals("Generic Error");

    }

    @Test
    void checkPinTestOK(){

        UserSicRequest request = new UserSicRequest();
        request.setUsername("username");
        request.setPsw("psw");
        request.setEmail("mail");

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        Mockito.when(userWebClient.checkPinUser(Mockito.any())).thenReturn(dbRespo);

        var resp = userService.checkPinUser(request);

        assert resp.getUserKey().equals("userKey1");

    }

    @Test
    void checkPinTestKO(){

        UserSicRequest request = new UserSicRequest();
        request.setUsername("username");
        request.setPsw("psw");
        request.setEmail("mail");

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("KO-00");
        dbRespo.setUserKey("userKey1");
        dbRespo.setErrMsg("Pin Error");
        dbRespo.setErrType("Invalid_Pin");

        Mockito.when(userWebClient.checkPinUser(Mockito.any())).thenReturn(dbRespo);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userService.checkPinUser(request);
        });

        assert ex.getCaused().equals("Invalid_Pin");
        assert ex.getErrId().equals("UserCheckKO-02");
        assert ex.getMsg().equals("Pin Error");

    }

    @Test
    void statusTestOK(){

        StatusSicResponse statusResp = new StatusSicResponse();
        statusResp.setResponse("OK-00");
        statusResp.setRegistered(true);
        statusResp.setCertified(false);

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(statusResp);

        var resp = userService.statusUserSic("");

        assert  resp.getRegistered() == true;
        assert  resp.getCertified() == false;
    }

    @Test
    void getUserByUsernameTestOK(){

        GetUserByUsernResponse iResp = new GetUserByUsernResponse();
        iResp.setUsername("username");
        iResp.setUserKey("userKey");
        iResp.setEmail("mail");

        Mockito.when(userWebClient.getUserByUsername(Mockito.any())).thenReturn(iResp);

        var resp = userService.getUserByUsername("");

        assert  resp.getUsername().equals("username");
        assert  resp.getUserKey().equals("userKey");
    }

    @Test
    void getUserByUsernameTestKO(){

        Mockito.when(userWebClient.getUserByUsername(Mockito.any())).thenReturn(new GetUserByUsernResponse());

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userService.getUserByUsername("");;
        });

        assert ex.getMsg().equals("Username provided are not valid, not user associated");
        assert ex.getCaused().equals("User not found");
        assert ex.getErrId().equals("UserGetByKO-02");
    }


}
