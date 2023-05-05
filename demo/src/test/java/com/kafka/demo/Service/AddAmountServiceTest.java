package com.kafka.demo.Service;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.entity.BankAccSic;
import com.kafka.demo.model.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AddAmountServiceTest extends BaseBankTest {

    @Test
    void addMountPlusTestOK(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(123456);
        acc.setType("debit");
        acc.setCognome("cognomeAdd");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyAdd");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyAdd");
        sic.setCertified(true);
        sic.setAccNumber(123456);

        bankAccSicRepo.save(sic);

       var resp = addAmountService.add_removeAmount("userkeyAdd",40.00,false);

       assert resp.msg().equals("change completed succefully");
       assert bankAccRepo.findByUserKey("userkeyAdd").getAmountAviable() == 90.00;
    }

    @Test
    void addAmountLessTestOK(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(1234567);
        acc.setType("debit");
        acc.setCognome("cognomeLess");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyLess");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyLess");
        sic.setCertified(true);
        sic.setAccNumber(1234567);

        bankAccSicRepo.save(sic);

        var resp = addAmountService.add_removeAmount("userkeyLess",40.00,true);

        assert resp.msg().equals("change completed succefully");
        assert bankAccRepo.findByUserKey("userkeyLess").getAmountAviable() == 10.00;
    }


    @Test
    void addAmountPlusTestKO(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(12345678);
        acc.setType("debit");
        acc.setCognome("cognomePlusKO");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyPlusKO");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyPlusKO");
        sic.setCertified(false);
        sic.setAccNumber(12345678);

        bankAccSicRepo.save(sic);

        CustomExcept ex = assertThrows( CustomExcept.class, () ->{
            addAmountService.add_removeAmount("userkeyPlusKO",40.00,true);
        });

        assert ex.getStatus().is4xxClientError();
        assert ex.getCaused().equals("Acc no certified");
    }
}
