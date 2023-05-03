package com.kafka.demodb.service.internal;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.Item;
import com.kafka.demodb.model.request.ItemRequest;
import com.kafka.demodb.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class ItemCrudService {

    @Autowired
    ItemRepository itemRepository;

    public Item saveItem(ItemRequest request){

        Item item = new Item();
        item.setDesctiption(request.getDesctiption());
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());

        return itemRepository.save(item);
    }

    public UpdateQuantitiSummary updateQuantity(String name, long quantity){

        try{
            var item = itemRepository.findItemByName(name);

            var finalQuant = item.getQuantity() + quantity;
            item.setQuantity(finalQuant);
            itemRepository.save(item);
            return new UpdateQuantitiSummary(name,finalQuant,item.getId(),"OK-00");
        }catch(Exception e){
            throw new CustomError("Internal_Error","Error on updateQuantitu with Error: "+ e.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Item getItemByName(String name){
        return itemRepository.findItemByName(name);
    }

    public Item getItemByDescription(String desctiption){
        return itemRepository.findItemByDesctiption(desctiption);
    }
    public UpdateQuantitiSummary decreaseQuantity(String name, long quantity){

       var item = itemRepository.findItemByName(name);

       if(ObjectUtils.isEmpty(item))
           throw new CustomError("NoItem", "No item present",LocalDateTime.now(),HttpStatus.BAD_REQUEST);

       if(item.getQuantity() < quantity)
           return new UpdateQuantitiSummary(name,item.getQuantity(),item.getId(),"Quantity_too_low");
       else{
           var finalQuant = item.getQuantity() - quantity;
           item.setQuantity(finalQuant);
           itemRepository.save(item);
           return new UpdateQuantitiSummary(name,finalQuant,item.getId(),"OK-00");
       }
    }
    public record UpdateQuantitiSummary(String name,long quantity,long id, String result){};





}
