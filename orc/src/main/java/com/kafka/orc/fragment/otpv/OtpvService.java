package com.kafka.orc.fragment.otpv;

import com.kafka.orc.client.OtpvWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CheckOtpvRequest;
import com.kafka.orc.model.fragment.response.CheckOtpvResponse;
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


    public CheckOtpvResponse checkOtpv(String userKey, String trxId,String otp){

        CheckOtpvRequest request = new CheckOtpvRequest();
        request.setOtp(otp);
        request.setUserKey(userKey);
        request.setTrxId(trxId);

        var resp = otpvWebClient.checkOtpv(request);

        if(ObjectUtils.isEmpty(resp))
            throw new OrcError("CheckOtpv_Void","check otpv resp is empty","CheckOtpvKO-02");


        if(!resp.getResult().equals("Otp validate"))
            throw new OrcError("Invalid_Otp",resp.getResponseMsg(),"CheckOtpvKO-03");

        return resp;
    }
}
