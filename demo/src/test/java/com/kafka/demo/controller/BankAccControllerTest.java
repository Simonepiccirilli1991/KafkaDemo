package com.kafka.demo.controller;

import com.kafka.demo.BaseBankTest;
import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.error.ErrorExc;
import com.kafka.demo.model.entity.BankAccSic;
import com.kafka.demo.model.entity.BankAccount;
import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.model.response.StatusResponse;
import com.kafka.demo.service.AddAmountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccControllerTest extends BaseBankTest {

    @Autowired
    MockMvc mvc;


    @Test
    void registerAccTestOK() throws Exception{

        AccountRequest request = new AccountRequest();
        request.setType("deposit");
        request.setNome("nome1");
        request.setCognome("cognome1");
        request.setEmail("mail1");
        request.setUserKey("userkey1Cont");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, BaseBankResponse.class);

        assert resp1.getResult().equals("BankAccSic registered");
        assert bankAccRepo.findByUserKey("userkey1Cont").getAmountAviable().equals(0.00);
    }

    @Test
    void registerTestOK() throws Exception{

        AccountRequest request = new AccountRequest();
        request.setType("deposit");
        request.setNome("nome2");
        request.setCognome("cognome2");
        request.setEmail("mail2");
        request.setUserKey("userkey2");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, BaseBankResponse.class);

        assert resp1.getResult().equals("BankAccSic registered");

        var response2 = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/certify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("userkey2"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp2 = mapper.readValue(response2, BaseBankResponse.class);

        assert resp2.getMsg().equals("user acc update certified");

        assert bankAccSicRepo.findByUserKey("userkey2").isCertified() == true;
    }

    @Test
    void statusTestOK() throws Exception{

        BankAccSic sic = new BankAccSic();
        sic.setCertified(false);
        sic.setAccNumber(1234);
        sic.setUserKey("userKeyStatusCon");
        bankAccSicRepo.save(sic);

        BankAccount acc = new BankAccount();
        acc.setAmountAviable(0.00);
        acc.setNome("nomeStatus");
        acc.setType("debit");
        acc.setCognome("cognomeStatus");
        acc.setDataApertura(LocalDateTime.now());
        acc.setEmail("emailStatus");
        acc.setUserKey("userKeyStatusCon");
        acc.setAccNumber(1234);

        bankAccRepo.save(acc);

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("userKeyStatusCon"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, StatusResponse.class);

        assert resp.getBankAccInfo().amountAviable().equals(0.00);
        assert resp.getBankAccSicInfo().certified() == false;
        assert resp.getBankAccSicInfo().accNumber() == 1234;
    }

    @Test
    void statusVoidTestOK() throws Exception{

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("voids"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, StatusResponse.class);

        assert resp.getNotPresent() == true;

    }

    @Test
    void addAmountPlusTestOK() throws Exception{

        BankAccount acc = new BankAccount();
        acc.setAccNumber(123456);
        acc.setType("debit");
        acc.setCognome("cognomeAdd");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyAddCon");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyAddCon");
        sic.setCertified(true);
        sic.setAccNumber(123456);

        bankAccSicRepo.save(sic);

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/amount?userkey=userkeyAddCon&amount=40.00&remove=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, AddAmountService.AmountResponse.class);

        assert resp.msg().equals("change completed succefully");
        assert bankAccRepo.findByUserKey("userkeyAddCon").getAmountAviable() == 90.00;
    }

    @Test
    void amountLessTestOK() throws Exception{

        BankAccount acc = new BankAccount();
        acc.setAccNumber(1234567);
        acc.setType("debit");
        acc.setCognome("cognomeLessCon");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyLessCon");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyLessCon");
        sic.setCertified(true);
        sic.setAccNumber(1234567);

        bankAccSicRepo.save(sic);

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/amount?userkey=userkeyLessCon&amount=40.00&remove=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, AddAmountService.AmountResponse.class);

        assert resp.msg().equals("change completed succefully");
        assert bankAccRepo.findByUserKey("userkeyLessCon").getAmountAviable() == 10.00;
    }

    @Test
    void addAmounTestKO() throws Exception{

        BankAccount acc = new BankAccount();
        acc.setAccNumber(12345678);
        acc.setType("debit");
        acc.setCognome("cognomePlusKO");
        acc.setNome("nomeAdd");
        acc.setAmountAviable(50.00);
        acc.setUserKey("userkeyPlusKOCon");
        acc.setDataApertura(LocalDateTime.now());

        bankAccRepo.save(acc);

        BankAccSic sic = new BankAccSic();
        sic.setUserKey("userkeyPlusKOCon");
        sic.setCertified(false);
        sic.setAccNumber(12345678);

        bankAccSicRepo.save(sic);

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/amount?userkey=userkeyPlusKOCon&amount=40.00&remove=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();

        var ex = mapper.readValue(response, ErrorExc.class);


        assert ex.getCause().equals("Acc no certified");

    }
}
