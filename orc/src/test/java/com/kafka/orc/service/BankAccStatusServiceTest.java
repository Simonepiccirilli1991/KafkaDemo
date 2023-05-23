package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.constants.Action;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.BankSicAcc;
import com.kafka.orc.model.fragment.SicSession;
import com.kafka.orc.model.fragment.response.GetSessionResponse;
import com.kafka.orc.model.fragment.response.StatusBankAccResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class BankAccStatusServiceTest extends BaseOrcTest {



    @Test
    void getBankStatusTestOK(){

        BankSicAcc sic = new BankSicAcc();
        sic.setCertified(true);

        StatusBankAccResponse statResp = new StatusBankAccResponse();
        statResp.setNotPresent(false);
        statResp.setBankAccSicInfo(sic);

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);
        Mockito.when(bankAccWebClient.getStatus(Mockito.any())).thenReturn(statResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","sessionId");
        var resp = bankAccStatusService.statusPostLoginBank("asd",header);

        assert resp.equals(Action.PERFORM);
    }

    @Test
    void getBankStatusTestOK2(){

        BankSicAcc sic = new BankSicAcc();
        sic.setCertified(false);

        StatusBankAccResponse statResp = new StatusBankAccResponse();
        statResp.setNotPresent(false);
        statResp.setBankAccSicInfo(sic);

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);
        Mockito.when(bankAccWebClient.getStatus(Mockito.any())).thenReturn(statResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","sessionId");
        var resp = bankAccStatusService.statusPostLoginBank("asd",header);

        assert resp.equals(Action.BANKCERTIFY);
    }

    @Test
    void getStatusBankTestKO(){

        StatusBankAccResponse statResp = new StatusBankAccResponse();
        statResp.setNotPresent(true);

        SicSession session = new SicSession();
        session.setScope("l2");

        GetSessionResponse sessResp = new GetSessionResponse();
        sessResp.setSession(session);

        Mockito.when(cacheWebClient.getSession(Mockito.any())).thenReturn(sessResp);
        Mockito.when(bankAccWebClient.getStatus(Mockito.any())).thenReturn(statResp);

        HttpHeaders header = new HttpHeaders();
        header.add("sessionId","sessionId");

        OrcError ex = Assertions.assertThrows(OrcError.class, () ->{
            bankAccStatusService.statusPostLoginBank("asd",header);
        });

        assert ex.getCaused().equals("BankAcc_Missing");
        assert ex.getErrId().equals("StatusBankRegKO-02");
        assert ex.getMsg().equals("User bank account are not present");
    }

}
