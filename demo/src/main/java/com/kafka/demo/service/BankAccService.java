package com.kafka.demo.service;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.service.internal.BankAccCrudService;
import com.kafka.demo.service.internal.BankAccSicCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankAccService {

    @Autowired
    BankAccCrudService bankAccCrudService;
    @Autowired
    BankAccSicCrudService bankAccSicCrudService;


    // nei giri di registrazione ecertifica non lanciamo eccezzione , gestisce orchestrator, unico caso e se request non e valida!
    public BaseBankResponse registerBankAcc(AccountRequest request){

        if(ObjectUtils.isEmpty(request.getEmail()) || ObjectUtils.isEmpty(request.getNome())
        || ObjectUtils.isEmpty(request.getType()) || ObjectUtils.isEmpty(request.getUserKey())
        || ObjectUtils.isEmpty(request.getEmail()))
            throw new CustomExcept("Invalid_Request", "Invalid request, missing parameter", HttpStatus.BAD_REQUEST);

        var resp = bankAccCrudService.createAcc(request);
        if(resp.getIsError())
            return resp;
        // mi serve acc number ma non lo torno, quindi in caso non sia errore te lo ritrovi nel msg e fai cast!
        var accNumber = Integer.parseInt(resp.getMsg());
        var iResp = bankAccSicCrudService.insertBankAccSic(request.getUserKey(),accNumber);

        if(iResp.getIsError())
            return resp;

        return new BaseBankResponse("BankAccSic registered","Account created",false);
    }


    // non gestisco verifica otp o errori , la gestisce orchestrator che ha gia validato tutto
    public BaseBankResponse certifySic(String userKey){

        if(ObjectUtils.isEmpty(userKey))
            throw new CustomExcept("Invalid_Request", "Invalid request, missing parameter", HttpStatus.BAD_REQUEST);

        var resp = bankAccSicCrudService.certifyAccSic(userKey);
        return resp;
    }


}
