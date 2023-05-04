package com.kafka.demo.service;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.service.internal.BankAccCrudService;
import com.kafka.demo.service.internal.BankAccSicCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AddAmountService {

    @Autowired
    private BankAccCrudService bankAccCrudService;
    @Autowired
    private BankAccSicCrudService bankAccSicCrudService;


    public AmountResponse add_removeAmount(String userKey,Double amount,Boolean decrease){

        var userAcc = bankAccCrudService.getBankAccByUserKey(userKey);
        var accSic = bankAccSicCrudService.getBankAccSIcByUserKey(userKey);

        if(ObjectUtils.isEmpty(userAcc) || ObjectUtils.isEmpty(accSic))
            throw new CustomExcept("no user register","No user registered acc / sic", HttpStatus.FORBIDDEN);

        if(!accSic.certified())
            throw new CustomExcept("Acc no certified","Acc must be certified first", HttpStatus.FORBIDDEN);

        var isRemove = (ObjectUtils.isEmpty(decrease)) ? false : decrease;

        if(!isRemove)
            bankAccCrudService.addAmount(userKey,amount);
        else
            bankAccCrudService.decreaseAmount(userKey,amount);

        return new AmountResponse("200-ok","change completed succefully");
    }

    public record AmountResponse(String result,String msg){}
}
