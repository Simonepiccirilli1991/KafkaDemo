package com.kafka.demoorc.controller;

import com.kafka.demoorc.model.request.PaymentRequest;
import com.kafka.demoorc.model.response.PaymentResponse;
import com.kafka.demoorc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class SagaOrcController {

    @Autowired
    PaymentService paymentService;


    @PostMapping("/pay")
    ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest request,
                                            @RequestHeader HttpHeaders header){
        return ResponseEntity.ok(paymentService.payment(request));
    }
}
