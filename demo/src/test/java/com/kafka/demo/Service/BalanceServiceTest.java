package com.kafka.demo.Service;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BalanceServiceTest extends BaseBankTest {


    @Test
    void getBalanceTestOK(){

        BankAccount acc = new BankAccount();
        acc.setAccNumber(123456);
        acc.setType("debit");
        acc.setCognome("cognomeAdd");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyBalance");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        var resp = balanceService.getBalanceAcc("userkeyBalance");

        assert resp.amountAviable().equals(50.00);
        assert resp.bankNumner() == 123456;
    }


    @Test
    void getBalanceTestKO(){

        CustomExcept ex = assertThrows( CustomExcept.class, () ->{
            balanceService.getBalanceAcc("userkeyBa");
        });

        assert ex.getCaused().equals("bank now found");
        assert ex.getMsg().equals("no bank acc fount for this userId");
    }
}
