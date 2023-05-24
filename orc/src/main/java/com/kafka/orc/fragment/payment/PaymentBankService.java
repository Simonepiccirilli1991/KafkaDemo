package com.kafka.orc.fragment.payment;

import com.kafka.orc.client.PaymentOrcWebClient;
import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PaymentBankService {

    @Autowired
    PaymentOrcWebClient paymentOrcWebClient;

    public Boolean payment(PaymentRequest request){

        var resp = paymentOrcWebClient.payment(request);

        if(ObjectUtils.isEmpty(resp))
            throw new OrcError("Payment_Error","response from payment is empty","PaymentKO-02");

        return resp;
    }
}
