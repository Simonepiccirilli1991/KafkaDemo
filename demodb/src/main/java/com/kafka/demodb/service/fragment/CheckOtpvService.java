package com.kafka.demodb.service.fragment;

import com.kafka.demodb.client.OtpWebClient;
import org.springframework.stereotype.Service;

@Service
public class CheckOtpvService {


    private final OtpWebClient otpWebClient;

    public CheckOtpvService(OtpWebClient otpWebClient) {
        this.otpWebClient = otpWebClient;
    }

    public Boolean  checkOtp(String trxId, String userKey, String otp){
        return otpWebClient.validateOtp(trxId,userKey,otp);
    }
}
