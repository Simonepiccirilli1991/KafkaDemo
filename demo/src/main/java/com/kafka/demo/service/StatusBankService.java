package com.kafka.demo.service;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.response.StatusResponse;
import com.kafka.demo.service.internal.BankAccCrudService;
import com.kafka.demo.service.internal.BankAccSicCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class StatusBankService {

    @Autowired
    BankAccCrudService bankAccCrudService;
    @Autowired
    BankAccSicCrudService bankAccSicCrudService;


    public StatusResponse getBankStatus(String userKey){

        BankAccCrudService.GetBankAccSummaryFilter resp;
        // porcata fatta per non modificare il metodo chiamato
        try {
             resp = bankAccCrudService.getBankAccByUserKey(userKey);
        }catch(CustomExcept e){
            if(e.getCaused().equals("bank now found"))
                return new StatusResponse(true);
            else
                throw e;
        }


        // non e possibile che esista solo 1 dei 2, se si lancaire eccezzione
        var iResp = bankAccSicCrudService.getBankAccSIcByUserKey(userKey);
        if(ObjectUtils.isEmpty(iResp))
            throw new CustomExcept("Bank sic not exist","Error acc exist but no a securety one", HttpStatus.INTERNAL_SERVER_ERROR);

        return new StatusResponse(false,resp,iResp);
    }
}
