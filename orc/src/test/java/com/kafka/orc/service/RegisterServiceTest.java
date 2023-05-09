package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import com.kafka.orc.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterServiceTest extends BaseOrcTest {

    @Test
    void registerUserTestOK(){

        BaseDbResponse dbRespo = new BaseDbResponse();
        dbRespo.setResult("OK-00");
        dbRespo.setUserKey("userKey1");

        BaseBankResponse bankResponse = new BaseBankResponse();
        bankResponse.setResult("OK-00");
        bankResponse.setAccNumber(12345);
        bankResponse.setMsg("messaggio");
        bankResponse.setIsError(false);

        Mockito.when(userWebClient.registerSic(Mockito.any())).thenReturn(dbRespo);
        Mockito.when(bankAccWebClient.registerBankUser(Mockito.any())).thenReturn(bankResponse);

        Mockito.when(userWebClient.statusSic(Mockito.any())).thenReturn(new StatusSicResponse());

        RegisterRequest request = new RegisterRequest();
        request.setCognome("cognome");
        request.setPsw("psw1");
        request.setEmail("email@mail");
        request.setUsername("user1");
        request.setType("debit");

        var response = registrationService.registerUT(request);

        assert response.userAccNumber() == 12345;
        assert  response.userKey().equals("userKey1");
    }
}
