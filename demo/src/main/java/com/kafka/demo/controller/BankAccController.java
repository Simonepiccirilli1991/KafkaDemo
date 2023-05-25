package com.kafka.demo.controller;

import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.request.PaymentRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.model.response.PaymentResponse;
import com.kafka.demo.model.response.StatusResponse;
import com.kafka.demo.service.*;
import com.kafka.demo.service.internal.BankAccCrudService;
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
    @Autowired
    BalanceService balanceService;
    @Autowired
    PaymentService paymentService;


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

    @GetMapping("/getbalance/{userKey}")
    public ResponseEntity<BankAccCrudService.GetBankAccSummaryFilter> getBalanceAcc(@PathVariable ("userKey") String userKey){

        return ResponseEntity.ok(balanceService.getBalanceAcc(userKey));
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest request){
        return ResponseEntity.ok(paymentService.payment(request));
    }
}
