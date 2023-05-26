package com.kafka.demoorc.service;

import com.kafka.demoorc.model.request.PaymentRequest;
import com.kafka.demoorc.model.response.PaymentResponse;
import com.kafka.demoorc.service.fragment.GetItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    GetItemService itemService;
    public PaymentResponse payment(PaymentRequest request){

        var item = itemService.getItem(request.getItem());

        var itemQuant = item.getItem().getQuantity();
        var itemPrice = item.getItem().getPrice();

        if(itemQuant < request.getItemAmount())
            return new PaymentResponse("not enought item", false, "Not enought quantity to buy");

        var finalPrice = itemPrice * request.getItemAmount();

        // condizioni sono soddisfatte, posso updatare item e provare a fare pagamento, se pagamento non va a buon fine rollback item


        return null;
    }
}
