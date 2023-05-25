package com.kafka.demo.Service;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.model.entity.BankAccount;
import com.kafka.demo.model.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class PaymentServiceTest extends BaseBankTest {


    @Test
    void paymentTestOK(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(1234567);
        acc.setType("debit");
        acc.setCognome("cognomeLess");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(150.00);
        acc.setUserKey("userttpay");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccount acc2 = new BankAccount();
        acc2.setAccNumber(1234567);
        acc2.setType("debit");
        acc2.setCognome("cognomeLess");
        acc2.setNome("nomeAdd");
        acc2.setAmountAviable(50.00);
        acc2.setUserKey("useryyvendor");
        acc2.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc2);


        PaymentRequest request = new PaymentRequest();
        request.setPayAmount(120.00);
        request.setUserPay("userttpay");
        request.setUserReceiv("useryyvendor");

        var resp = paymentService.payment(request);

        assert resp.getTransaction() == true;

        var accUp = bankAccRepo.findByUserKey("userttpay");
        var accUp2 = bankAccRepo.findByUserKey("useryyvendor");

        assert accUp.getAmountAviable().equals(30.00);
        assert accUp2.getAmountAviable().equals(170.00);
    }

    @Test
    void paymentTestKO(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(1234567);
        acc.setType("debit");
        acc.setCognome("cognomeLess");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(150.00);
        acc.setUserKey("userttpay2");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccount acc2 = new BankAccount();
        acc2.setAccNumber(1234567);
        acc2.setType("debit");
        acc2.setCognome("cognomeLess");
        acc2.setNome("nomeAdd");
        acc2.setAmountAviable(50.00);
        acc2.setUserKey("useryyvendor2");
        acc2.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc2);


        PaymentRequest request = new PaymentRequest();
        request.setPayAmount(180.00);
        request.setUserPay("userttpay2");
        request.setUserReceiv("useryyvendor2");

        var resp = paymentService.payment(request);

        assert resp.getTransaction() == false;
        assert resp.getCauseMgs().equals("Not enough amount aviable");

        var accUp = bankAccRepo.findByUserKey("userttpay2");
        var accUp2 = bankAccRepo.findByUserKey("useryyvendor2");

        assert accUp.getAmountAviable().equals(150.00);
        assert accUp2.getAmountAviable().equals(50.00);
    }
}
