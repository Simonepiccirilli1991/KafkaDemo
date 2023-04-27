package com.kafka.otpv.controller;

import com.kafka.otpv.model.request.CheckOtpvRequest;
import com.kafka.otpv.model.request.GenerateOtpvRequest;
import com.kafka.otpv.model.response.CheckOtpvSummaryResponse;
import com.kafka.otpv.service.CheckOtpvService;
import com.kafka.otpv.service.GenerateOtpvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("api/v1/otp")
public class OtpvController {


    private final CheckOtpvService checkOtpvService;
    private final GenerateOtpvService generateOtpvService;

    public OtpvController(CheckOtpvService checkOtpvService, GenerateOtpvService generateOtpvService) {
        this.checkOtpvService = checkOtpvService;
        this.generateOtpvService = generateOtpvService;
    }


    @PostMapping("/generate")
    public ResponseEntity<GenerateOtpvService.GenerateOtpv> generateOtp(@RequestBody GenerateOtpvRequest request){
        return ResponseEntity.ok(generateOtpvService.generateOtpv(request.getUserKey()));
    }

    @PostMapping("/check")
    public ResponseEntity<CheckOtpvSummaryResponse> checkOtp(@RequestBody CheckOtpvRequest request) throws Exception {
        return ResponseEntity.ok(checkOtpvService.checkOtp(request));
    }
}
