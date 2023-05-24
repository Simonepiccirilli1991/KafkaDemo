package com.kafka.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.demo.repo.BankAccRepo;
import com.kafka.demo.repo.BankAccSicRepo;
import com.kafka.demo.service.AddAmountService;
import com.kafka.demo.service.BalanceService;
import com.kafka.demo.service.BankAccService;
import com.kafka.demo.service.StatusBankService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseBankTest {

    @Autowired
    protected BalanceService balanceService;
    @Autowired
    protected BankAccRepo bankAccRepo;
    @Autowired
    protected BankAccSicRepo bankAccSicRepo;
    @Autowired
    protected BankAccService bankAccService;
    @Autowired
    protected StatusBankService statusBankService;
    @Autowired
    protected AddAmountService addAmountService;

    protected ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
}
