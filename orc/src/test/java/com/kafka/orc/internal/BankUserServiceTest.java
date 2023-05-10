package com.kafka.orc.internal;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankUserServiceTest extends BaseOrcTest {

    @Test
    void registerUserBankTestOK(){

        BaseBankResponse iResp = new BaseBankResponse();
        iResp.setIsError(false);
        iResp.setResult("register complete");
        iResp.setMsg("daje");

        Mockito.when(bankAccWebClient.registerBankUser(Mockito.any())).thenReturn(iResp);

        var resp = bankUserService.registerUser(new UserAccRequest());

        assert resp.getMsg().equals("daje");
        assert resp.getResult().equals("register complete");
    }

    @Test
    void registerUserBankTestKO(){

        BaseBankResponse iResp = new BaseBankResponse();
        iResp.setIsError(true);
        iResp.setMsg("Error on register");
        iResp.setResult("Error on register");

        Mockito.when(bankAccWebClient.registerBankUser(Mockito.any())).thenReturn(iResp);

        OrcError ex = assertThrows(OrcError.class, () -> {
            var resp = bankUserService.registerUser(new UserAccRequest());
        });

        assert ex.getMsg().equals("Error on register");
        assert ex.getErrId().equals("UserBankRegKO-02");
        assert ex.getCaused().equals("Error on register");
    }
}
