package com.kafka.demo.service.internal;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.entity.BankAccSic;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.repo.BankAccSicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BankAccSicCrudService {

    @Autowired
    BankAccSicRepo bankAccSicRepo;


    public BaseBankResponse insertBankAccSic(String userKey,int accNumber){

        BankAccSic sic = new BankAccSic();
        sic.setAccNumber(accNumber);
        sic.setUserKey(userKey);
        sic.setCertified(true);

        bankAccSicRepo.save(sic);

        return new BaseBankResponse("200-ok","bank sic created", false);
    }

    public BaseBankResponse certifyAccSic(String userKey){

        var user = bankAccSicRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(user))
            return new BaseBankResponse("Error","No user acc found", true);

        user.setCertified(true);
        bankAccSicRepo.save(user);
        return new BaseBankResponse("200-ok","user acc update certified", false);
    }
    public BankAccSicFilter getBankAccSIcByUserKey(String userKey){

        var user = bankAccSicRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(user))
            throw new CustomExcept("no acc found","No account for userKey provided", HttpStatus.FORBIDDEN);

        return new BankAccSicFilter(userKey,user.getAccNumber(),user.isCertified());
    }

    public BankAccSicFilter getBankAccSIcByAccNUmbery(int accNumber){

        var user = bankAccSicRepo.findByAccNumber(accNumber);
        if(user.isEmpty())
            throw new CustomExcept("no acc found","No account for userKey provided", HttpStatus.FORBIDDEN);

        return new BankAccSicFilter(user.get().getUserKey(),accNumber,user.get().isCertified());
    }
    public record BankAccSicFilter(String userKey,int accNumber,boolean certified){}
}
