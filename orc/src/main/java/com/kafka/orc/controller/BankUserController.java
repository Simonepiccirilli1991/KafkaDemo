package com.kafka.orc.controller;

import com.kafka.orc.constants.Action;
import com.kafka.orc.service.BankAccStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/orc/v1/bank")
public class BankUserController {

    private final BankAccStatusService bankAccStatusService;


    @PostMapping("/status")
    ResponseEntity<Action> statusBank(@RequestBody String userKey, @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(bankAccStatusService.statusPostLoginBank(userKey,header));
    }
}
