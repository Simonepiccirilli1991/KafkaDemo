package com.kafka.demodb.controller;

import com.kafka.demodb.model.request.CertifyMailRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.UpdateSecuretuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/securety")
public class SecuretyController {

    private final UpdateSecuretuService updateSecuretuService;

    public SecuretyController(UpdateSecuretuService updateSecuretuService) {
        this.updateSecuretuService = updateSecuretuService;
    }

    @PostMapping("/certify")
    public ResponseEntity<BaseDbResponse> certifyMail(@RequestBody CertifyMailRequest request){
        return ResponseEntity.ok(updateSecuretuService.certifyMailUser(request.getUserKey(),request.getMail(),request.getOtp(), request.getTrxId()));
    }


}
