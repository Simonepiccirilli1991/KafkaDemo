package com.kafka.demo.service;

import com.kafka.demo.error.CustomExcept;
import com.kafka.demo.model.request.PaymentRequest;
import com.kafka.demo.model.response.PaymentResponse;
import com.kafka.demo.repo.BankAccRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class PaymentService {

    @Autowired
    BankAccRepo bankAccRepo;

    Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PaymentResponse payment(PaymentRequest request){

        var buyer = bankAccRepo.findByUserKey(request.getUserPay());

        if(ObjectUtils.isEmpty(buyer))
            throw new CustomExcept("no acc found for user pay","No account for userpay provided", HttpStatus.FORBIDDEN);

        var vendor = bankAccRepo.findByUserKey(request.getUserReceiv());

        if(ObjectUtils.isEmpty(vendor))
            throw new CustomExcept("no acc found for user vendor","No account for userreceiv provided", HttpStatus.FORBIDDEN);

        //checko se ha disponibilita su fondo
        if(buyer.getAmountAviable() >= request.getPayAmount()){

            var newBalanceBuyer = buyer.getAmountAviable() - request.getPayAmount();
            var newBalanceVendor = vendor.getAmountAviable() + request.getPayAmount();

            bankAccRepo.paymentAccount(buyer.getUserKey(),newBalanceBuyer,vendor.getUserKey(),newBalanceVendor);

            return new PaymentResponse(true,"transaction completed");
        }else
            return new PaymentResponse(false,"Not enough amount aviable");
    }
}
