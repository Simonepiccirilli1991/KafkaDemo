package com.kafka.otpv.service;

import com.kafka.otpv.exception.OtpvError;
import com.kafka.otpv.model.request.CheckOtpvRequest;
import com.kafka.otpv.model.response.CheckOtpvSummaryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@Service
public class CheckOtpvService {


    private final OtpvCacheService otpvCacheService;

    public CheckOtpvService(OtpvCacheService otpvCacheService) {
        this.otpvCacheService = otpvCacheService;
    }

    public CheckOtpvSummaryResponse checkOtp(CheckOtpvRequest request) throws TimeoutException {


        var otpv = otpvCacheService.get(request.getTrxId());

        if(ObjectUtils.isEmpty(otpv))
            throw new OtpvError(HttpStatus.CONFLICT, "No value present in cache", "Invalid_trxId");


        if(LocalDateTime.now().isAfter(otpv.getStartTime().plusMinutes(1)))
            throw new TimeoutException();

        if(request.getOtp().equals(otpv.getOtp()))
            return new CheckOtpvSummaryResponse("Otp validate", "Otp validate, access granted");
        else
            throw new OtpvError(HttpStatus.FORBIDDEN, "Invalid otp provided, retry or resend", "Invalid_Otp");

    }
}
