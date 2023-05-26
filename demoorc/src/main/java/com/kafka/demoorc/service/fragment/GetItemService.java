package com.kafka.demoorc.service.fragment;

import com.kafka.demoorc.client.ItemWebClient;
import com.kafka.demoorc.model.fragment.request.GetItemRequest;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GetItemService {

    @Autowired
    ItemWebClient itemWebClient;


    public GetItemResponse getItem(String itemName){

        GetItemRequest request = new GetItemRequest();
        request.setName(itemName);

        var resp = itemWebClient.getItem(request);

        if(ObjectUtils.isEmpty(resp) || !resp.getResult().equals("OK-00"))
            throw new RuntimeException();

        if(ObjectUtils.isEmpty(resp.getItem()) || ObjectUtils.isEmpty(resp.getItem().getPrice()))
            throw new RuntimeException();

        return resp;
    }
}
