package com.kafka.demoorc.client;

import com.kafka.demoorc.exception.SagaOrcExcept;
import com.kafka.demoorc.model.fragment.request.GetItemRequest;
import com.kafka.demoorc.model.fragment.request.TransactionPaymentRequest;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
import com.kafka.demoorc.model.fragment.response.TransactionPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PaymentWebClient {

    @Autowired
    WebClient webClient;

    public TransactionPaymentResponse transactionPayment(TransactionPaymentRequest request){

        var resp = webClient.post().uri("TODO")//TODO: aggiungere endpoint corretto
                .bodyValue(request)
                .retrieve().bodyToMono(TransactionPaymentResponse.class)
                .onErrorResume( e -> {
                    throw new SagaOrcExcept("error on calling transactionPayment",e.getMessage());
                });

        return resp.block();
    }
}
