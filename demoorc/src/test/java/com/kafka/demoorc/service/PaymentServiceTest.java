package com.kafka.demoorc.service;

import com.kafka.demoorc.client.ItemWebClient;
import com.kafka.demoorc.client.PaymentWebClient;
import com.kafka.demoorc.model.fragment.Item;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
import com.kafka.demoorc.model.fragment.response.TransactionPaymentResponse;
import com.kafka.demoorc.model.request.PaymentRequest;
import com.kafka.demoorc.model.response.UpdateQuantitiSummary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;
    @MockBean
    ItemWebClient itemWebClient;
    @MockBean
    PaymentWebClient paymentWebClient;


    @Test
    void paymentTestOK(){

        PaymentRequest request = new PaymentRequest();
        request.setItem("itemName");
        request.setItemAmount(2);
        request.setUserPay("userPay");
        request.setUserReceive("userReceive");


        Item item = new Item();
        item.setId(1);
        item.setName("itemName");
        item.setPrice(230.00);
        item.setQuantity(19);

        GetItemResponse itemResp = new GetItemResponse();
        itemResp.setItem(item);
        itemResp.setResult("OK-00");

        TransactionPaymentResponse payResp = new TransactionPaymentResponse();
        payResp.setTransaction(true);

        Mockito.when(itemWebClient.getItem(Mockito.any())).thenReturn( itemResp);
        Mockito.when(itemWebClient.updateItem(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new UpdateQuantitiSummary("nameItem",10,1,"OK-00"));
        Mockito.when(paymentWebClient.transactionPayment(Mockito.any())).thenReturn(payResp);

        var resp = paymentService.payment(request);

        assert resp.getTransaction() == true;
    }
}
