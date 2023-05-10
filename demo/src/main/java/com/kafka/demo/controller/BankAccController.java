package com.kafka.demo.controller;

import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.model.response.StatusResponse;
import com.kafka.demo.service.BankAccService;
import com.kafka.demo.service.StatusBankService;
import com.kafka.demo.service.AddAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bank")
public class BankAccController {


    @Autowired
    BankAccService bankAccService;
    @Autowired
    StatusBankService statusBankService;
    @Autowired
    AddAmountService addAmountService;


    @PostMapping("/register")
    public ResponseEntity<BaseBankResponse> registerAccSic(@RequestBody AccountRequest request){
        return ResponseEntity.ok(bankAccService.registerBankAcc(request));
    }

    @PostMapping("/certify")
    public ResponseEntity<BaseBankResponse> certify(@RequestBody String userKey){
        return ResponseEntity.ok(bankAccService.certifySic(userKey));
    }

    @PostMapping("/status")
    public ResponseEntity<StatusResponse> status(@RequestBody String userKey){
        return ResponseEntity.ok(statusBankService.getBankStatus(userKey));
    }

    @PostMapping("/amount")
    public ResponseEntity<AddAmountService.AmountResponse> amountAcc(@RequestParam ("userkey") String userKey,
                                                                     @RequestParam ("amount") Double amount,
                                                                     @RequestParam ("remove") Boolean isRemove){
        return ResponseEntity.ok(addAmountService.add_removeAmount(userKey,amount,isRemove));
    }
}