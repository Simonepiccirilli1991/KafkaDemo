package com.kafka.demo.Service;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.repo.BankAccRepo;
import com.kafka.demo.repo.BankAccSicRepo;
import com.kafka.demo.service.BankAccService;
import com.kafka.demo.service.StatusBankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BankAccServiceTest extends BaseBankTest {

    @Test
    void registerBankAccTestOK(){

        AccountRequest request = new AccountRequest();
        request.setType("deposit");
        request.setNome("nome1");
        request.setCognome("cognome1");
        request.setEmail("mail1");
        request.setUserKey("userkey1");

        var resp = bankAccService.registerBankAcc(request);

        assert resp.getResult().equals("BankAccSic registered");
        assert bankAccRepo.findByUserKey("userkey1").getAmountAviable().equals(0.00);
    }

    @Test
    void register_certifyTestOK(){

        AccountRequest request = new AccountRequest();
        request.setType("deposit");
        request.setNome("nome2");
        request.setCognome("cognome2");
        request.setEmail("mail2");
        request.setUserKey("userkey2");

        var resp = bankAccService.registerBankAcc(request);

        assert resp.getResult().equals("BankAccSic registered");

        var iResp = bankAccService.certifySic("userkey2");

        assert iResp.getMsg().equals("user acc update certified");

        assert bankAccSicRepo.findByUserKey("userkey2").isCertified() == true;
    }
}
