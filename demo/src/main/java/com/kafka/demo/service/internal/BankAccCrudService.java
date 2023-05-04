package com.kafka.demo.service.internal;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.entity.BankAccount;
import com.kafka.demo.model.request.AccountRequest;
import com.kafka.demo.model.response.BaseBankResponse;
import com.kafka.demo.repo.BankAccRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class BankAccCrudService {

    @Autowired
    BankAccRepo bankAccRepo;

    public BaseBankResponse createAcc(AccountRequest request){

        BankAccount account = new BankAccount();
        account.setEmail(request.getEmail());
        account.setNome(request.getNome());
        account.setUserKey(request.getUserKey());
        account.setCognome(request.getCognome());
        account.setType(request.getType());
        account.setDataApertura(LocalDateTime.now());
        account.setAccNumber(request.getUserKey().hashCode());
        var amount = (ObjectUtils.isEmpty(request.getAmountAviable())) ? 0.00 : request.getAmountAviable();
        account.setAmountAviable(amount);

        try{
            bankAccRepo.save(account);
            return new BaseBankResponse("200-ok",""+account.getAccNumber(), false);
        }catch(Exception e){
            return new BaseBankResponse("error",e.getMessage(),true);
        }
    }

    public GetBankAccSummaryFilter getBankAccByUserKey(String userKey){

        var userBank = bankAccRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(userBank))
            throw new CustomExcept("bank now found","no bank acc fount for this userId", HttpStatus.FORBIDDEN);

        return new GetBankAccSummaryFilter(userKey,userBank.getAccNumber(),userBank.getAmountAviable(),userBank.getEmail());
    }

    public void addAmount (String userKey, Double amount){

        var userBank = bankAccRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(userBank))
                throw new CustomExcept("bank now found","no bank acc fount for this userId", HttpStatus.FORBIDDEN);

        var totalAmount = userBank.getAmountAviable() + amount;

        userBank.setAmountAviable(totalAmount);

        bankAccRepo.save(userBank);
    }

    public void decreaseAmount (String userKey,Double amount){

        var userBank = bankAccRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(userBank))
            throw new CustomExcept("bank now found","no bank acc fount for this userId", HttpStatus.FORBIDDEN);

        if(userBank.getAmountAviable() < amount)
            throw new CustomExcept("not enought found","not enought amountAviable to cover the amount inc", HttpStatus.FORBIDDEN);

        var totalAmount = userBank.getAmountAviable() - amount;
        userBank.setAmountAviable(totalAmount);

        bankAccRepo.save(userBank);
    }
    public record GetBankAccSummaryFilter(String userKey,int bankNumner,Double amountAviable,String email){}
}
