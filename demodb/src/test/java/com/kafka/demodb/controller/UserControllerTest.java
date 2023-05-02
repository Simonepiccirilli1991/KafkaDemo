package com.kafka.demodb.controller;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.DbErrorHandler;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.entity.UserSecurity;
import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.GetUserService;
import com.kafka.demodb.service.StatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseDbTest {

    @Autowired
    protected MockMvc mvc;

    @Test
    void registerEGetUserTestOK() throws Exception{

        UserRequest request = new UserRequest();
        request.setUserKey("getUserKey");
        request.setEmail("getemail@mail.it");
        request.setPsw("getpsw123");
        request.setUsername("getUserProva");
        request.setUserKey("getUserKey");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        var response = mapper.readValue(resp, BaseDbResponse.class);

        assert response.getResult().equals("OK-00");

        var get = mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get/getUserProva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp2 = mapper.readValue(get, GetUserService.UserSummary.class);

        assert  resp2.email().equals("getemail@mail.it");
        assert  resp2.username().equals("getUserProva");
    }

    // -------------------------CHECKPIN ----------------------------------------///
    @Test
    void checkPinTestOK() throws Exception{

        UserRequest request = new UserRequest();
        request.setEmail("pin@mail.it");
        request.setPsw("psw123");
        request.setUsername("userPin");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        var response = mapper.readValue(resp, BaseDbResponse.class);

        assert response.getResult().equals("OK-00");

        var resp2 = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/checkpin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response2 = mapper.readValue(resp2, BaseDbResponse.class);

        assert response2.getResult().equals("OK-00");
    }

    @Test
    void checkPinTestKO() throws Exception{

        UserRequest request = new UserRequest();
        request.setEmail("pinKo@mail.it");
        request.setPsw("psw123");
        request.setUsername("userPinKo");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        var response = mapper.readValue(resp, BaseDbResponse.class);

        assert response.getResult().equals("OK-00");

        request.setPsw("ajeje");

        var checkPin = checkPinService.checkPinUser(request);

        assert checkPin.getResult().equals("ERKO-05");
        assert secCounterCrudService.getCounter(response.getUserKey()).getPswCounter() == 1;
    }

    // -----------------STATUS ----------------------------------------------------//

    @Test
    void statusCertifiedTestOK() throws Exception{

        UserSecurity userSec = new UserSecurity();
        userSec.setEmailCertified(true);
        userSec.setLastPsw("1234");
        userSec.setUserKey("userKeyStatus");

        userSecRepo.save(userSec);

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameStatus");
        user.setUserKey("userKeyStatus");
        user.setEmail("mailStatus");
        userCrudService.insertUser(user);

        UserRequest request = new UserRequest();
        request.setEmail("mailStatus");
        request.setUserKey("userKeyStatus");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, StatusService.StatusResponse.class);

        assert Boolean.TRUE.equals(resp.certified());
        assert Boolean.TRUE.equals(resp.registered());
    }

    @Test
    void statusRegisteredTestOK() throws Exception{

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameStatusRegister");
        user.setUserKey("userKeyStatusRegister");
        user.setEmail("mailStatusRegister");

        userCrudService.insertUser(user);

        UserRequest request = new UserRequest();
        request.setEmail("mailStatusRegister");
        request.setUserKey("userKeyStatusRegister");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, StatusService.StatusResponse.class);

        assert Boolean.FALSE.equals(resp.certified());
        assert Boolean.TRUE.equals(resp.registered());
    }

    @Test
    void statusAbsentTestOK() throws Exception {

        UserRequest request = new UserRequest();
        request.setEmail("mailAbsent");
        request.setUserKey("userAbsent");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, StatusService.StatusResponse.class);

        assert Boolean.FALSE.equals(resp.certified());
        assert Boolean.FALSE.equals(resp.registered());

    }

    @Test
    void statusTestKO() throws Exception {

        UserRequest request = new UserRequest();
        request.setEmail("");
        request.setUserKey("");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/user/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, DbErrorHandler.CustomExceptionResponse.class);

        assert  resp.errTp().equals("Invalid_Request");
        assert  resp.errMgs().equals("Invalid requst, missing parameter");

    }
}
