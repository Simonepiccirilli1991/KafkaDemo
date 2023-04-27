package com.kafka.otpv.service;

import com.kafka.otpv.model.OtpvDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class GenerateOtpvService {

    private final GenerateKey generateKeyService;
    private final OtpvCacheService otpvCacheService;

    public GenerateOtpvService(GenerateKey generateKeyService, OtpvCacheService otpvCacheService) {
        this.generateKeyService = generateKeyService;
        this.otpvCacheService = otpvCacheService;
    }

    public String generateOtpv(String userKey){

        OtpvDto otpv = new OtpvDto();

        // trxId
        var trxId =  generateKeyService.generateTrxid(userKey);

        var otp = randomString(7);

        otpv.setOtp(otp);
        otpv.setTrxId(trxId);
        otpv.setUserKey(userKey);
        otpv.setStartTime(LocalDateTime.now());

        otpvCacheService.insert(trxId, otpv);

        return trxId;
    }



    // usati per generare otp
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}
