package com.kafka.orc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.constants.Action;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.CreateSessionResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import com.kafka.orc.model.request.LoginRequest;
import com.kafka.orc.model.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseOrcTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void loginTestUsernameTestOK() throws Exception {

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

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/orc/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse();

        var header = resp.getHeader("sessionId");

        var  response  = mapper.readValue(resp.getContentAsString(), LoginResponse.class);

        assert response.action().equals(Action.CERTIFY);
        assert header.equals("1234");
    }
}
