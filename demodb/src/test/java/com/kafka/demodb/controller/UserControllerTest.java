package com.kafka.demodb.controller;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.GetUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseDbTest {

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

}
