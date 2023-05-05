package com.kafka.orc.fragment.usersic;

import com.kafka.orc.client.SicWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.fragment.request.ChangePswSicRequest;
import com.kafka.orc.model.fragment.request.RetrivePswRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSicService {

    @Autowired
    SicWebClient sicWebClient;


   public BaseDbResponse certifyUserSic(CertifyMailSicRequest request){

        var resp = sicWebClient.cerityUserSic(request);
        if(!resp.getResult().equals("OK-00"))
            throw new OrcError(resp.getErrType(),resp.getErrMsg(),"UserCertKO-02");

        return resp;
   }

    public BaseDbResponse changePSw(ChangePswSicRequest request){

        var resp = sicWebClient.changePsw(request);
        if(!resp.getResult().equals("OK-00"))
            throw new OrcError(resp.getErrType(),resp.getErrMsg(),"UserChangeKO-02");

        return resp;
    }

    public String retrivePsw(RetrivePswRequest request){

        var resp = sicWebClient.retrivePsw(request);
        if(!resp.getResult().equals("OK-00"))
            throw new OrcError(resp.getErrType(),resp.getErrMsg(),"UserChangeKO-02");

        return resp.getUserKey(); // psw e stat messa in parametro userKey
    }


}
