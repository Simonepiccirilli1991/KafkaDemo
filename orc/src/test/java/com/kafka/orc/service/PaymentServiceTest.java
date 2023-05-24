package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.model.fragment.SicSession;
import com.kafka.orc.model.fragment.response.GetSessionResponse;
import com.kafka.orc.model.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class PaymentServiceTest extends BaseOrcTest {


    @Test
    void paymentServiceTestOK(){

        PaymentRequest request = new PaymentRequest();
        request.setItem("item");
        request.setItemAmount(2);
        request.setUserPay("userPay");
        request.setUserReceive("userReceive");

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        HttpHeaders headers = new HttpHeaders();
        headers.add("sessionId", "sessionId");

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);

        Mockito.when(paymentOrcWebClient.payment(Mockito.any())).thenReturn(true);

        var resp = paymentService.payment(request, headers);

        assert resp == true;
    }

    @Test
    void paymentServiceTestKO(){

    }
}
