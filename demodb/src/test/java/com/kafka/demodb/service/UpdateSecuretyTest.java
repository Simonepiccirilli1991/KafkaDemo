package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.SecurityCounter;
import com.kafka.demodb.model.entity.UserAccount;
import com.kafka.demodb.model.entity.UserSecurity;
import com.kafka.demodb.model.request.CertifyMailRequest;
import com.kafka.demodb.model.request.ChangePswRequest;
import com.kafka.demodb.model.request.RetrivePswRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UpdateSecuretyTest extends BaseDbTest {

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
        var resp = getUserService.getUser("","userKeyChange");

        assert resp.getUser().getPsw().equals("12345");
    }

    @Test
    void changePswTestKO(){

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

        CustomError response =  assertThrows(CustomError.class, () -> {
            updateSecuretuService.changePsw(request);
        });
        assert  response.getErrTp().equals("Invalid_Otp");
        assert  response.getErrMsg().equals("InvalidOtp");
        assert  response.getStatus().is4xxClientError();

        assert  secCounterRepo.findByUserKey("userKeyChangeKO").getPswCounter() == 1;
    }

    @Test
    void retryvePswTestOK(){

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

        var response = updateSecuretuService.retrivePsw(request);

        assert response.getUserKey().equals("1234");
    }

    @Test
    void retrivePswTestKO(){

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

        CustomError response =  assertThrows(CustomError.class, () -> {
            updateSecuretuService.retrivePsw(request);
        });
        assert  response.getErrTp().equals("Invalid_Otp");
        assert  response.getErrMsg().equals("InvalidOtp");
        assert  response.getStatus().is4xxClientError();
    }
}
