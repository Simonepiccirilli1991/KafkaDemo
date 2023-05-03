package com.kafka.demodb.service;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.Item;
import com.kafka.demodb.model.request.ItemRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.ItemCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemCrudService itemCrudService;

    public ItemService(ItemCrudService itemCrudService) {
        this.itemCrudService = itemCrudService;
    }

    public BaseDbResponse addItem(ItemRequest request){

        if(ObjectUtils.isEmpty(request.getPrice()) || ObjectUtils.isEmpty(request.getDesctiption()) || ObjectUtils.isEmpty(request.getQuantity())
        || ObjectUtils.isEmpty(request.getName()))
            throw new CustomError("Invalid_Request","Invalid request, missing parameter", LocalDateTime.now(), HttpStatus.BAD_REQUEST);

        itemCrudService.saveItem(request);

        return new BaseDbResponse("OK-00");
    }

    public GetItemResponse getItem(ItemRequest request){

        if(ObjectUtils.isEmpty(request.getName()) && ObjectUtils.isEmpty(request.getDesctiption()))
            throw new CustomError("Invalid_Request","Invalid request, missing desciption e name", LocalDateTime.now(), HttpStatus.BAD_REQUEST);

        Optional<Item> itemName = Optional.of(itemCrudService.getItemByName(request.getName()));

        if(itemName.isPresent())
            return new GetItemResponse("OK-00",itemName.get(),"");

        Optional<Item> itemDescr = Optional.of(itemCrudService.getItemByDescription(request.getDesctiption()));

        if(itemDescr.isEmpty())
            return new GetItemResponse("ERKO-01",null,"no such item present");
        else
            return new GetItemResponse("OK-00",itemDescr.get(),"");
    }

    public ItemCrudService.UpdateQuantitiSummary updateQuantity(String name, long quantity, boolean isRemove){

        if(ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(quantity))
            throw new CustomError("Invalid_Request","Missing parameter on request",LocalDateTime.now(),HttpStatus.BAD_REQUEST);

        boolean check = (ObjectUtils.isEmpty(isRemove)) ? false : isRemove;

        if(isRemove)
            return itemCrudService.decreaseQuantity(name,quantity);
        else
            return itemCrudService.updateQuantity(name,quantity);
    }

    public List<Item> getItemFilterByMaxPrice(double price){

        var filteredItem = itemCrudService.getAll().stream().dropWhile(e -> e.getPrice() > price).toList();
        return filteredItem;
    }
    public record GetItemResponse(String result, Item item, String errMsg){}
}
