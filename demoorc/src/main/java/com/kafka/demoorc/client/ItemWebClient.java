package com.kafka.demoorc.client;

import com.kafka.demoorc.model.fragment.request.GetItemRequest;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
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
                    throw new RuntimeException();
                });

        return resp.block();
    }
}
