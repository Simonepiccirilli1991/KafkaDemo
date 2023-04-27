package com.kafka.otpv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.otpv.exception.ApiError;
import com.kafka.otpv.model.request.CheckOtpvRequest;
import com.kafka.otpv.model.request.GenerateOtpvRequest;
import com.kafka.otpv.model.response.CheckOtpvSummaryResponse;
import com.kafka.otpv.service.GenerateOtpvService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Test
    void generateOtpvTestOK() throws Exception {

        GenerateOtpvRequest request = new GenerateOtpvRequest();
        request.setUserKey("user1");

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/otp/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, GenerateOtpvService.GenerateOtpv.class);

        assert response.trxId() != null;
        assert response.otp() != null;

    }

    @Test
    void checkOtpTestOK() throws Exception{

        GenerateOtpvRequest request = new GenerateOtpvRequest();
        request.setUserKey("user2");

        var respInsert = mvc.perform(MockMvcRequestBuilders.post("/api/v1/otp/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(respInsert, GenerateOtpvService.GenerateOtpv.class);


        CheckOtpvRequest otpvRequest = new CheckOtpvRequest();
        otpvRequest.setOtp(response.otp());
        otpvRequest.setTrxId(response.trxId());
        otpvRequest.setUserKey("user2");

        var respCheck = mvc.perform(MockMvcRequestBuilders.post("/api/v1/otp/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(otpvRequest)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        var resp = mapper.readValue(respCheck, CheckOtpvSummaryResponse.class);

        assert resp.responseMsg().equals("Otp validate, access granted");
        assert resp.result().equals("Otp validate");
    }

    @Test
    void checkOtpTestKO() throws Exception{

        GenerateOtpvRequest request = new GenerateOtpvRequest();
        request.setUserKey("user3");

        var respInsert = mvc.perform(MockMvcRequestBuilders.post("/api/v1/otp/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(respInsert, GenerateOtpvService.GenerateOtpv.class);


        CheckOtpvRequest otpvRequest = new CheckOtpvRequest();
        otpvRequest.setOtp(response.otp());
        otpvRequest.setTrxId(response.trxId());
        otpvRequest.setUserKey("user2");

        // mi serve che l'otp scada quindi faccio aspettare 1 minuto
        Thread.sleep(60 * 1000);

        var respCheck = mvc.perform(MockMvcRequestBuilders.post("/api/v1/otp/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(otpvRequest)))
                .andExpect(status().isPreconditionFailed()).andReturn().getResponse().getContentAsString();


        var resp = mapper.readValue(respCheck, ApiError.class);

        assert  resp.getErrMsg().equals("Otpv is expired, resend and retry");
        assert resp.getErrTp().equals("Otp_Epired");
        assert resp.getApiAcronim().equals("otpv0.logic.");
    }


}
