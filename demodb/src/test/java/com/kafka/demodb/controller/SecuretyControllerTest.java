package com.kafka.demodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.exception.DbErrorHandler;
import com.kafka.demodb.model.entity.SecurityCounter;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.entity.UserSecurity;
import com.kafka.demodb.model.request.CertifyMailRequest;
import com.kafka.demodb.model.request.ChangePswRequest;
import com.kafka.demodb.model.request.RetrivePswRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuretyControllerTest extends BaseDbTest {

    @Test
    void certifyMailTestOK() throws Exception{

        SecurityCounter counter = new SecurityCounter();
        counter.setEmailCounter(0);
        counter.setPswCounter(0);
        counter.setUserKey("userKeyCertify");

        secCounterRepo.save(counter);

        UserSecurity user = new UserSecurity();
        user.setEmailCertified(false);
        user.setLastPsw("1234");
        user.setUserKey("userKeyCertify");

        userSecRepo.save(user);

        CertifyMailRequest request = new CertifyMailRequest();
        request.setMail("mail");
        request.setOtp("otp");
        request.setUserKey("userKeyCertify");
        request.setTrxId("trxId");

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(true);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/certify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, BaseDbResponse.class);
        assert response.getResult().equals("OK-00 - Otp Verified");

        assert userSecRepo.findByUserKey("userKeyCertify").getEmailCertified().equals(true);
    }

    @Test
    void certiMailTestKO() throws Exception{

        SecurityCounter counter = new SecurityCounter();
        counter.setEmailCounter(0);
        counter.setPswCounter(0);
        counter.setUserKey("userKeyCertifyKO");

        secCounterRepo.save(counter);

        UserSecurity user = new UserSecurity();
        user.setEmailCertified(false);
        user.setLastPsw("1234");
        user.setUserKey("userKeyCertifyKO");

        userSecRepo.save(user);

        CertifyMailRequest request = new CertifyMailRequest();
        request.setMail("mail");
        request.setOtp("otp");
        request.setUserKey("userKeyCertifyKO");
        request.setTrxId("trxId");

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(false);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/certify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, DbErrorHandler.CustomExceptionResponse.class);

        assert  response.errTp().equals("Invalid_Otp");
        assert  response.errMgs().equals("InvalidOtp");
    }

    //----------------CHANGE ---------------------------------------------//
    @Test
    void changePswTestOK() throws Exception{

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("username123");
        user.setUserKey("userKeyChange");
        user.setEmail("mail");
        userCrudService.insertUser(user);

        UserSecurity userSec = new UserSecurity();
        userSec.setLastPsw("1234");
        userSec.setEmailCertified(false);
        userSec.setUserKey("userKeyChange");
        userSecRepo.save(userSec);

        ChangePswRequest request = new ChangePswRequest();
        request.setPsw("12345");
        request.setOtp("otp");
        request.setUserKey("userKeyChange");
        request.setTrxId("trxId");

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(true);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, BaseDbResponse.class);

        assert response.getResult().equals("OK-00 - Psw changed");

        var iResp = getUserService.getUser("","userKeyChange");

        assert iResp.getUser().getPsw().equals("12345");
    }

    @Test
    void changePswTestKO() throws Exception{

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("username123KO");
        user.setUserKey("userKeyChangeKO");
        user.setEmail("mailKO");
        userCrudService.insertUser(user);

        UserSecurity userSec = new UserSecurity();
        userSec.setLastPsw("1234");
        userSec.setEmailCertified(false);
        userSec.setUserKey("userKeyChangeKO");
        userSecRepo.save(userSec);

        ChangePswRequest request = new ChangePswRequest();
        request.setPsw("12345");
        request.setOtp("otp");
        request.setUserKey("userKeyChangeKO");
        request.setTrxId("trxId");

        SecurityCounter counter = new SecurityCounter();
        counter.setEmailCounter(0);
        counter.setPswCounter(0);
        counter.setUserKey("userKeyChangeKO");

        secCounterRepo.save(counter);

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(false);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, DbErrorHandler.CustomExceptionResponse.class);

        assert  response.errTp().equals("Invalid_Otp");
        assert  response.errMgs().equals("InvalidOtp");

        assert  secCounterRepo.findByUserKey("userKeyChangeKO").getPswCounter() == 1;
    }

    //-------------------------RETRIVE -----------------------------------------///
    @Test
    void retryvePswTestOK() throws Exception{

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameRetrive");
        user.setUserKey("userKeyRetrive");
        user.setEmail("mailRetrive");
        userCrudService.insertUser(user);

        UserSecurity userSec = new UserSecurity();
        userSec.setLastPsw("1234");
        userSec.setEmailCertified(false);
        userSec.setUserKey("userKeyRetrive");
        userSecRepo.save(userSec);

        RetrivePswRequest request = new RetrivePswRequest();
        request.setOtp("otp");
        request.setUserKey("userKeyRetrive");
        request.setTrxId("trxId");
        request.setTrxId("trxId");

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(true);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/retrive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, BaseDbResponse.class);

        assert response.getUserKey().equals("1234");
    }

    @Test
    void retrivePswTestKO() throws Exception{

        UserAccount user = new UserAccount();
        user.setPsw("1234");
        user.setUsername("usernameRetriveKO");
        user.setUserKey("userKeyRetriveKO");
        user.setEmail("mailRetriveKO");
        userCrudService.insertUser(user);

        UserSecurity userSec = new UserSecurity();
        userSec.setLastPsw("1234");
        userSec.setEmailCertified(false);
        userSec.setUserKey("userKeyRetriveKO");
        userSecRepo.save(userSec);

        SecurityCounter counter = new SecurityCounter();
        counter.setEmailCounter(0);
        counter.setPswCounter(0);
        counter.setUserKey("userKeyRetriveKO");
        secCounterRepo.save(counter);

        RetrivePswRequest request = new RetrivePswRequest();
        request.setOtp("otp");
        request.setUserKey("userKeyRetriveKO");
        request.setTrxId("trxId");
        request.setTrxId("trxId");

        Mockito.when(otpWebClient.validateOtp(any(),any(),any())).thenReturn(false);

        var resp = mvc.perform(MockMvcRequestBuilders.post("/api/v1/securety/retrive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();

        var response = mapper.readValue(resp, DbErrorHandler.CustomExceptionResponse.class);

        assert  response.errTp().equals("Invalid_Otp");
        assert  response.errMgs().equals("InvalidOtp");


    }
}
