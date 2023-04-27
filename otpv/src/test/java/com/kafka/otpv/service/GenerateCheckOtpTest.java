package com.kafka.otpv.service;

import com.kafka.otpv.exception.OtpvError;
import com.kafka.otpv.model.request.CheckOtpvRequest;
import com.kafka.otpv.model.request.GenerateOtpvRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GenerateCheckOtpTest {

    @Autowired
    GenerateOtpvService generateOtpvService;
    @Autowired
    CheckOtpvService checkOtpvService;

    @Test
    void generateOtpvTestOK(){

        GenerateOtpvRequest request = new GenerateOtpvRequest();
        request.setUserKey("user1");

        var resp = generateOtpvService.generateOtpv(request.getUserKey());

        assert resp != null;

        System.out.println(resp);

    }

    @Test
    void generateECheckTestOK() throws Exception {

        var resp = generateOtpvService.generateOtpv("user2");

        CheckOtpvRequest request = new CheckOtpvRequest();
        request.setOtp(resp.otp());
        request.setUserKey("user2");
        request.setTrxId(resp.trxId());

        var response = checkOtpvService.checkOtp(request);

        assert response.responseMsg().equals("Otp validate, access granted");
        assert response.result().equals("Otp validate");

    }

    @Test
    void generateECheckTestKO()throws Exception{

        var resp = generateOtpvService.generateOtpv("user2");

        CheckOtpvRequest request = new CheckOtpvRequest();
        request.setOtp("otp");
        request.setUserKey("user2");
        request.setTrxId(resp.trxId());

        OtpvError ex = assertThrows(OtpvError.class, () -> {
            checkOtpvService.checkOtp(request);
        });

        assert ex.getStatus().is4xxClientError();
        assert ex.getErrMsg().equals("Invalid otp provided, retry or resend");
        assert ex.getErrTp().equals("Invalid_Otp");
    }

    @Test
    void checkTimeoutTestKO() throws Exception{

        var resp = generateOtpvService.generateOtpv("user3");

        Thread.sleep(60 * 1000);

        CheckOtpvRequest request = new CheckOtpvRequest();
        request.setOtp("otp");
        request.setUserKey("user2");
        request.setTrxId(resp.trxId());

        TimeoutException ex = assertThrows(TimeoutException.class, () -> {
            checkOtpvService.checkOtp(request);
        });


        System.out.println(ex);
    }

}
