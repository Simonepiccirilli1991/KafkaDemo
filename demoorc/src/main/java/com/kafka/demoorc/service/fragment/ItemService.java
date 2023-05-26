package com.kafka.demoorc.service.fragment;

import com.kafka.demoorc.client.ItemWebClient;
import com.kafka.demoorc.exception.SagaOrcExcept;
import com.kafka.demoorc.model.fragment.request.GetItemRequest;
import com.kafka.demoorc.model.fragment.response.GetItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ItemService {

    @Autowired
    ItemWebClient itemWebClient;


    public GetItemResponse getItem(String itemName){

        GetItemRequest request = new GetItemRequest();
        request.setName(itemName);

        var resp = itemWebClient.getItem(request);

        if(ObjectUtils.isEmpty(resp) || !resp.getResult().equals("OK-00"))
            throw new SagaOrcExcept("No item","Error on retrivyng item");

        if(ObjectUtils.isEmpty(resp.getItem()) || ObjectUtils.isEmpty(resp.getItem().getPrice()))
            throw new SagaOrcExcept("item is empty/ no price","Error on retrivyng item values");

        return resp;
    }

    public void updateItemAcquire(String name, long quantity){

        var resp = itemWebClient.updateItem(name,quantity,false);

        if(ObjectUtils.isEmpty(resp) || !resp.result().equals("OK-00"))
            throw new SagaOrcExcept("error on updateItemAcquire","Error on updateItemAcquire");

    }

    public void updateItemRollback(String name, long quantity){

        var resp = itemWebClient.updateItem(name,quantity,true);

        if(ObjectUtils.isEmpty(resp) || !resp.result().equals("OK-00"))
            throw new SagaOrcExcept("Error on updateItemRollback","Error on updateItemRollback");

    }
}
