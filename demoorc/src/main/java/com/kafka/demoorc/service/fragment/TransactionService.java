package com.kafka.demoorc.service.fragment;

import com.kafka.demoorc.client.PaymentWebClient;
import com.kafka.demoorc.exception.SagaOrcExcept;
import com.kafka.demoorc.model.fragment.request.TransactionPaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class TransactionService {

    @Autowired
    PaymentWebClient paymentWebClient;

    public void transactionPayment(String userPay,String userReceiv, Double amount){

        TransactionPaymentRequest request = new TransactionPaymentRequest();
        request.setPayAmount(amount);
        request.setUserReceiv(userReceiv);
        request.setUserPay(userPay);

        var resp = paymentWebClient.transactionPayment(request);

        if(ObjectUtils.isEmpty(resp) || !resp.getTransaction())
            throw new SagaOrcExcept("Error on transaction calling",resp.getCauseMgs());
    }
}
