package com.kafka.orc.internal;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.fragment.request.ChangePswSicRequest;
import com.kafka.orc.model.fragment.request.RetrivePswRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserSicServiceTest extends BaseOrcTest {

    @Test
    void certifyUserSicTestOK(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        Mockito.when(sicWebClient.cerityUserSic(Mockito.any())).thenReturn(dbRespo);

        var resp = userSicService.certifyUserSic(new CertifyMailSicRequest());

        assert resp.getUserKey().equals("userKey1");
        assert resp.getResult().equals("OK-00");
    }

    @Test
    void certifyUserSicTestKO(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("KO-00");
        dbRespo.setUserKey("userKey1");
        dbRespo.setErrMsg("Otp Error");
        dbRespo.setErrType("Invalid_Otp");

        Mockito.when(sicWebClient.cerityUserSic(Mockito.any())).thenReturn(dbRespo);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userSicService.certifyUserSic(new CertifyMailSicRequest());
        });

        assert ex.getCaused().equals("Invalid_Otp");
        assert ex.getErrId().equals("UserCertKO-02");
        assert ex.getMsg().equals("Otp Error");

    }

    @Test
    void changePswUserSicTestOK(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        Mockito.when(sicWebClient.changePsw(Mockito.any())).thenReturn(dbRespo);

        var resp = userSicService.changePSw(new ChangePswSicRequest());

        assert resp.getUserKey().equals("userKey1");
        assert resp.getResult().equals("OK-00");
    }

    @Test
    void changePswUserSicTestKO(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("KO-00");
        dbRespo.setUserKey("userKey1");
        dbRespo.setErrMsg("Psw change Error");
        dbRespo.setErrType("Invalid_Psw");

        Mockito.when(sicWebClient.changePsw(Mockito.any())).thenReturn(dbRespo);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userSicService.changePSw(new ChangePswSicRequest());
        });

        assert ex.getCaused().equals("Invalid_Psw");
        assert ex.getErrId().equals("UserChangeKO-02");
        assert ex.getMsg().equals("Psw change Error");

    }

    @Test
    void retryvePswUserSicTestOK(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        Mockito.when(sicWebClient.retrivePsw(Mockito.any())).thenReturn(dbRespo);

        var resp = userSicService.retrivePsw(new RetrivePswRequest());

        assert resp.equals("userKey1");
    }

    @Test
    void retryvePswUserSicTestKO(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("KO-00");
        dbRespo.setUserKey("userKey1");
        dbRespo.setErrMsg("Psw retryve Error");
        dbRespo.setErrType("Invalid_Otp");

        Mockito.when(sicWebClient.retrivePsw(Mockito.any())).thenReturn(dbRespo);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = userSicService.retrivePsw(new RetrivePswRequest());
        });

        assert ex.getCaused().equals("Invalid_Otp");
        assert ex.getErrId().equals("UserChangeKO-02");
        assert ex.getMsg().equals("Psw retryve Error");

    }
}
