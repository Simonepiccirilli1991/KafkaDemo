package com.kafka.demo;

import com.kafka.demo.repo.BankAccRepo;
import com.kafka.demo.repo.BankAccSicRepo;
import com.kafka.demo.service.AddAmountService;
import com.kafka.demo.service.BankAccService;
import com.kafka.demo.service.StatusBankService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseBankTest {

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
}
