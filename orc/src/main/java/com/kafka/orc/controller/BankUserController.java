package com.kafka.orc.controller;

import com.kafka.orc.constants.Action;
import com.kafka.orc.model.fragment.GetBalanceAccResponse;
import com.kafka.orc.model.fragment.request.BalanceRequest;
import com.kafka.orc.model.fragment.response.AmountBankResponse;
import com.kafka.orc.model.request.PaymentRequest;
import com.kafka.orc.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/orc/v1/bank")
public class BankUserController {

    private final BankAccStatusService bankAccStatusService;
    private final GetBalanceService getBalanceService;
    private final AddBalanceService addBalanceService;
    private final RemoveBalanceService removeBalanceService;
    private final PaymentService paymentService;


    @PostMapping("/status")
    public ResponseEntity<Action> statusBank(@RequestBody String userKey, @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(bankAccStatusService.statusPostLoginBank(userKey,header));
    }

    @PostMapping("/status/balance")
    public ResponseEntity<GetBalanceAccResponse> getBalanceAcc(@RequestBody String userKey){
        return ResponseEntity.ok(getBalanceService.getBalanceAcc(userKey));
    }

    @PostMapping("/addBalance")
    public ResponseEntity<AmountBankResponse> addBalance(@RequestBody BalanceRequest request,
                                                         @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(addBalanceService.addBalance(request, header));
    }

    @PostMapping("/removeBalance")
    public ResponseEntity<AmountBankResponse> removeBalance(@RequestBody BalanceRequest request,
                                                         @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(removeBalanceService.removeBalance(request, header));
    }

    @PostMapping("/payment")
    public ResponseEntity<Boolean> payment(@RequestBody PaymentRequest request,
                                           @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(paymentService.payment(request, header));
    }
}
