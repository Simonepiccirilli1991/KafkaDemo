package com.kafka.demoorc.service;

import com.kafka.demoorc.exception.SagaOrcExcept;
import com.kafka.demoorc.model.request.PaymentRequest;
import com.kafka.demoorc.model.response.PaymentResponse;
import com.kafka.demoorc.service.fragment.ItemService;
import com.kafka.demoorc.service.fragment.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    ItemService itemService;
    @Autowired
    TransactionService transactionService;


    public PaymentResponse payment(PaymentRequest request){

        var item = itemService.getItem(request.getItem());

        var itemQuant = item.getItem().getQuantity();
        var itemPrice = item.getItem().getPrice();

        if(itemQuant < request.getItemAmount())
            return new PaymentResponse("not enought item", false, "Not enought quantity to buy");

        var finalPrice = itemPrice * request.getItemAmount();

        // condizioni sono soddisfatte, posso updatare item e provare a fare pagamento, se pagamento non va a buon fine rollback item

        itemService.updateItemAcquire(item.getItem().getName(),request.getItemAmount());

        // ora effettuo pagamento
        try{

            transactionService.transactionPayment(request.getUserPay(),request.getUserReceive(),finalPrice);

        }catch(Exception e){

            //rollbacko e lancio eccezzione
            itemService.updateItemRollback(item.getItem().getName(),request.getItemAmount());
            throw new SagaOrcExcept("Exception happend during transaction",e.getMessage());
        }

        return new PaymentResponse("transaction completed",true,"OK-00");
    }
}
