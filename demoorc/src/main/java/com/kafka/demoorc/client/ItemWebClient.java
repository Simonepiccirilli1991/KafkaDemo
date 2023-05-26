package com.kafka.demoorc.client;

import com.kafka.demoorc.exception.SagaOrcExcept;
import com.kafka.demoorc.model.fragment.request.GetItemRequest;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
import com.kafka.demoorc.model.response.UpdateQuantitiSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ItemWebClient {

    @Autowired
    WebClient webClient;

    public GetItemResponse getItem(GetItemRequest request){

        var resp = webClient.post().uri("TODO")//TODO: aggiungere endpoint corretto
                .bodyValue(request)
                .retrieve().bodyToMono(GetItemResponse.class)
                .onErrorResume( e -> {
                    throw new SagaOrcExcept("error on calling getItem",e.getMessage());
                });

        return resp.block();
    }

    public UpdateQuantitiSummary updateItem(String name, long quantity, boolean isRemove){

        var resp = webClient.post().uri("TODO",name,quantity,isRemove)//TODO: aggiungere endpoint corretto con queey param
                .retrieve().bodyToMono(UpdateQuantitiSummary.class)
                .onErrorResume( e -> {
                    throw new SagaOrcExcept("error on calling updateItem",e.getMessage());
                });

        return resp.block();
    }
}
