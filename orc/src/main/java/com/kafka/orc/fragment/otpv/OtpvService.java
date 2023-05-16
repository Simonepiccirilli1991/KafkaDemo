package com.kafka.orc.fragment.otpv;

import com.kafka.orc.client.OtpvWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.response.GenerateOtpvResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OtpvService {

    @Autowired
    OtpvWebClient otpvWebClient;


    public GenerateOtpvResponse generateOtp(String userKey){

        var resp = otpvWebClient.generateOtpv(userKey);

        if(ObjectUtils.isEmpty(resp) || ObjectUtils.isEmpty(resp.getOtp()) || ObjectUtils.isEmpty(resp.getTrxId()))
            throw new OrcError("GenerateOtpv_Void","Generate otpv resp is empty","GenerateOtpvKO-02");

        return resp;
    }
}
