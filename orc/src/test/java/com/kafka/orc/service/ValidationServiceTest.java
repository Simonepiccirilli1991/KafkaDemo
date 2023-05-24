package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.client.CacheWebClient;
import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.CheckOtpvResponse;
import com.kafka.orc.model.request.ValidationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class ValidationServiceTest extends BaseOrcTest {


    @Test
    void validateCertifyTestOK(){

        ValidationRequest request = new ValidationRequest();
        request.setAction(Action.CERTIFY);
        request.setOtp("otp");
        request.setTrxId("trxId");
        request.setUserKey("userKye");

        BaseDbResponse certResp = new BaseDbResponse();
        certResp.setResult("OK-00");

        Mockito.when(sicWebClient.cerityUserSic(Mockito.any())).thenReturn(certResp);

        CacheWebClient.SessionUpdate sessResp = new CacheWebClient.SessionUpdate("sessionIdx", true);

        Mockito.when(cacheWebClient.updateSession(Mockito.any())).thenReturn(sessResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","SessionID");

        var resp = validationService.validationService(request, header);

        assert resp.equals(Action.CONSET);
    }

    @Test
    void validateCheckOtpvTestOK(){

        ValidationRequest request = new ValidationRequest();
        request.setAction(Action.CHECKOTP);
        request.setOtp("otp");
        request.setTrxId("trxId");
        request.setUserKey("userKye");

        CheckOtpvResponse otpvResponse = new CheckOtpvResponse();
        otpvResponse.setResult("Otp validate");

        Mockito.when(otpvWebClient.checkOtpv(Mockito.any())).thenReturn(otpvResponse);

        CacheWebClient.SessionUpdate sessResp = new CacheWebClient.SessionUpdate("sessionIdx", true);

        Mockito.when(cacheWebClient.updateSession(Mockito.any())).thenReturn(sessResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","SessionID");

        var resp = validationService.validationService(request, header);

        assert resp.equals(Action.CONSET);
    }


    @Test
    void validateInvalidRequestTestKO(){

        ValidationRequest request = new ValidationRequest();
        request.setAction(Action.REGISTER);
        request.setOtp("otp");
        request.setTrxId("trxId");
        request.setUserKey("userKye");

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","SessionID");

        OrcError ex = Assertions.assertThrows( OrcError.class, () -> {
            var resp = validationService.validationService(request, header);

        });

        assert ex.getMsg().equals("Invalid action provided");
        assert ex.getCaused().equals("Invalid_Action");
    }

    @Test
    void validateSessionTestOK(){


        ValidationRequest request = new ValidationRequest();
        request.setAction(Action.CHECKOTP);
        request.setOtp("otp");
        request.setTrxId("trxId");
        request.setUserKey("userKye");

        HttpHeaders header = new HttpHeaders();

        OrcError ex = Assertions.assertThrows( OrcError.class, () -> {
            var resp = validationService.validationService(request, header);

        });

        assert ex.getMsg().equals("SessionId is missing");
        assert ex.getCaused().equals("Session_Missing");
    }

    @Test
    void validateCheckOtpCertifyTestOK(){

        ValidationRequest request = new ValidationRequest();
        request.setAction(Action.BANKCERTIFY);
        request.setOtp("otp");
        request.setTrxId("trxId");
        request.setUserKey("userKye");

        CheckOtpvResponse otpvResponse = new CheckOtpvResponse();
        otpvResponse.setResult("Otp validate");

        Mockito.when(otpvWebClient.checkOtpv(Mockito.any())).thenReturn(otpvResponse);

        Mockito.when(cacheWebClient.checkValidSession(Mockito.any())).thenReturn(true);

        BaseBankResponse bankResp = new BaseBankResponse();
        bankResp.setIsError(false);

        Mockito.when(bankAccWebClient.certifyBankAcc(Mockito.any())).thenReturn(bankResp);
        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","SessionID");

        var resp = validationService.validationService(request, header);

        assert resp.equals(Action.BANKCERTIFIED);
    }
}
