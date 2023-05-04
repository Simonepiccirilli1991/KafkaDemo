package com.kafka.demo.Service;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.model.entity.BankAccSic;
import com.kafka.demo.model.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class StatusServiceTest extends BaseBankTest {


    @Test
    void statusPresentTestOK(){

        BankAccSic sic = new BankAccSic();
        sic.setCertified(false);
        sic.setAccNumber(1234);
        sic.setUserKey("userKeyStatus");
        bankAccSicRepo.save(sic);

        BankAccount acc = new BankAccount();
        acc.setAmountAviable(0.00);
        acc.setNome("nomeStatus");
        acc.setType("debit");
        acc.setCognome("cognomeStatus");
        acc.setDataApertura(LocalDateTime.now());
        acc.setEmail("emailStatus");
        acc.setUserKey("userKeyStatus");
        acc.setAccNumber(1234);

        bankAccRepo.save(acc);

        var resp = statusBankService.getBankStatus("userKeyStatus");

        assert resp.getBankAccInfo().amountAviable().equals(0.00);
        assert resp.getBankAccSicInfo().certified() == false;
        assert resp.getBankAccSicInfo().accNumber() == 1234;
    }

    @Test
    void statusVoidTestOK(){

        var resp = statusBankService.getBankStatus("userKeyStatusVoid");

        assert resp.getNotPresent() == true;
    }


}
