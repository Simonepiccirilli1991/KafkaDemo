package com.kafka.cache.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.cache.error.CacheError;
import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.SicSession;
import com.kafka.cache.model.request.SessionRequest;
import com.kafka.cache.service.SessionCacheService;
import com.kafka.cache.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    SessionCacheService sessionCacheService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void createSessionTestOK() throws Exception {

        SessionRequest request = new SessionRequest();
        request.setScope("l1");
        request.setUserKey("userKey");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/session/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse();

        var iResp = resp.getContentAsString();
        var header = resp.getHeader("sessionId");
        var response = mapper.readValue(iResp, SessionService.SessionResponse.class);

        assert response.success() == true;

        assert sessionCacheService.get(header) != null;
    }

    @Test
    void createSessionTestKO() throws Exception {

        SessionRequest request = new SessionRequest();
        request.setScope("l1");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/session/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, CacheError.class);

        assert response.errCause().equals("Generic_Error");
        assert response.errMsg().equals("Error on creating session");
    }

    @Test
    void updateSessionTestOK() throws Exception{

        SicSession session = new SicSession();
        session.setCreationDate(LocalDateTime.now());
        session.setScope("l1");
        session.setUpdate(false);
        session.setUserKey("userKey");

        sessionCacheService.insert("1234-update",session);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/session/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("sessionId","1234-update"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, SessionService.SessionResponse.class);
        assert response.success() == true;

        var sess = sessionCacheService.get("1234-update");

        assert sess.getUpdate() == true;
        assert sess.getScope().equals("l2");
    }

    @Test
    void updateSessionTestKO() throws Exception{

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/session/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("sessionId","1234-upd"))
                .andExpect(status().isInternalServerError()).andReturn().getResponse().getContentAsString();

        var ex = mapper.readValue(resp, CacheError.class);

        assert ex.errCause().equals("Session don't exist");
        assert ex.errMsg().equals("Error on updating session");
    }



}
