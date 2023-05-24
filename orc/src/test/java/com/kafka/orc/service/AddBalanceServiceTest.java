package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.SicSession;
import com.kafka.orc.model.fragment.request.BalanceRequest;
import com.kafka.orc.model.fragment.response.AmountBankResponse;
import com.kafka.orc.model.fragment.response.GetSessionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class AddBalanceServiceTest extends BaseOrcTest {

    @Test
    void addBalanceTestOK(){

        BalanceRequest request = new BalanceRequest();
        request.setAmount(100.00);
        request.setUserKey("userkey");

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        AmountBankResponse resp = new AmountBankResponse();
        resp.setMsg("msg");
        resp.setResult("200-OK");

        HttpHeaders headers = new HttpHeaders();
        headers.add("sessionId", "sessionId");

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);
        Mockito.when(bankAccWebClient.addORemoveBalance(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(resp);

        var response = addBalanceService.addBalance(request,headers);

        assert response.getResult().equals("200-OK");
        assert response.getMsg().equals("msg");
    }

    @Test
    void addBalanceTestKO(){

        BalanceRequest request = new BalanceRequest();
        request.setAmount(100.00);
        request.setUserKey("userkey");

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        AmountBankResponse resp = new AmountBankResponse();
        resp.setMsg("msg");
        resp.setResult("blabla");

        HttpHeaders headers = new HttpHeaders();
        headers.add("sessionId", "sessionId");

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);
        Mockito.when(bankAccWebClient.addORemoveBalance(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(resp);

        OrcError ex = Assertions.assertThrows(OrcError.class, () ->{
            addBalanceService.addBalance(request,headers);
        });

        assert ex.getMsg().equals("PI_MS_5000: Generic error");
        assert ex.getCaused().equals("Generic Error");
    }
}
