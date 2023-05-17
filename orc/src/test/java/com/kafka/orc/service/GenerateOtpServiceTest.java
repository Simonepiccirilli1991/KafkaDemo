package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.constants.Action;
import com.kafka.orc.model.fragment.response.GenerateOtpvResponse;
import com.kafka.orc.model.request.GenerateOtpvRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class GenerateOtpServiceTest extends BaseOrcTest {

    @Test
    void generateOtpTestOK(){

        GenerateOtpvRequest request = new GenerateOtpvRequest();
        request.setUserKey("userKey");
        request.setAction(Action.SENDOTP);

        Mockito.when(cacheWebClient.checkValidSession(Mockito.any())).thenReturn(true);

        GenerateOtpvResponse otpResp = new GenerateOtpvResponse();
        otpResp.setOtp("otp");
        otpResp.setTrxId("trxId");

        Mockito.when(otpvWebClient.generateOtpv(Mockito.any())).thenReturn(otpResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","sessionIdv1");

        var response = generateOtpService.generateOtpv(request,header);

        assert response.equals("trxId");
    }
}
