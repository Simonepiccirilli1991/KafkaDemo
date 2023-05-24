package com.kafka.orc.service;

import com.kafka.orc.BaseOrcTest;
import com.kafka.orc.model.fragment.GetBalanceAccResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetBalanceServiceTest extends BaseOrcTest {


    @Test
    void getBalanceTestOK(){

        GetBalanceAccResponse balance = new GetBalanceAccResponse();
        balance.setAmountAviable(200.00);
        balance.setBankNumner(1234);
        balance.setEmail("email");
        balance.setUserKey("userkye");

        Mockito.when(bankAccWebClient.getBalanceAcc(Mockito.any())).thenReturn(balance);

        var resp = getBalanceService.getBalanceAcc("daje");

        assert resp.getAmountAviable().equals(200.00);
        assert resp.getEmail().equals("email");

    }

    @Test
    void getBalanceTestKO(){

    }
}
