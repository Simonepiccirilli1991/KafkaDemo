package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.client.OtpWebClient;
import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.SecurityCounter;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.entity.UserSecurity;
import com.kafka.demodb.model.request.CertifyMailRequest;
import com.kafka.demodb.model.request.ChangePswRequest;
import com.kafka.demodb.model.response.CheckOtpvSummaryResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UpdateSecuretyTest extends BaseDbTest {

    @Autowired
    UpdateSecuretuService updateSecuretuService;
    @MockBean
    OtpWebClient otpWebClient;

    @Test
    void certifyMailTestOK(){

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

        var response = updateSecuretuService.certifyMailUser("userKeyCertify","mail","otp","trxId");

        assert response.getResult().equals("OK-00 - Otp Verified");

        assert userSecRepo.findByUserKey("userKeyCertify").getEmailCertified().equals(true);
    }

    @Test
    void certiMailTestKO(){

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

        CustomError response =  assertThrows(CustomError.class, () -> {
            updateSecuretuService.certifyMailUser("userKeyCertifyKO","mail","otp","trxId");
        });


        assert  response.getErrTp().equals("Invalid_Otp");
        assert  response.getErrMsg().equals("InvalidOtp");
        assert  response.getStatus().is4xxClientError();
    }

    @Test
    void changePswTestOK(){

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

        var response = updateSecuretuService.changePsw(request);

        assert response.getResult().equals("OK-00 - Psw changed");
        //TODO: vedere poi fix perche non updata psw;
        var resp = getUserService.getUser("","userKeyChange");

        assert resp.getUser().getPsw().equals("12345");
    }
}
