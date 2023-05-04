package com.kafka.demo.controller;

import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.service.BankAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bank")
public class BankAccController {


    @Autowired
    BankAccService bankAccService;


    @PostMapping("register")
    public ResponseEntity<BaseBankResponse> registerAccSic(@RequestBody AccountRequest request){
        return ResponseEntity.ok(bankAccService.registerBankAcc(request));
    }

    @PostMapping("certify")
    public ResponseEntity<>
}
