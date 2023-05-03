package com.kafka.demodb.service;

import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.model.entity.Item;
import com.kafka.demodb.model.request.ItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ItemServiceTest extends BaseDbTest {


    @Test
    void addItemTestOK(){

        ItemRequest request = new ItemRequest();
        request.setDesctiption("item description");
        request.setName("Item name");
        request.setPrice(100.00);
        request.setQuantity(9);

        var resp = itemService.addItem(request);

        assert resp.getResult().equals("OK-00");

        assert itemRepository.findItemByName("Item name").getPrice().equals(100.00);
    }

    @Test
    void addItemTestKO(){

        ItemRequest request = new ItemRequest();
        request.setDesctiption("item description");

        CustomError response =  assertThrows(CustomError.class, () -> {
            itemService.addItem(request);
        });
        assert response.getErrTp().equals("Invalid_Request");
    }

    @Test
    void updateItemMaxTestOK(){

        Item item = new Item();
        item.setDesctiption("item update max description");
        item.setName("Item update max name");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

       var resp = itemRepository.save(item);

       assert resp != null;
       assert resp.getQuantity() == 9;

       var response = itemService.updateQuantity("Item update max name", 2, false);

       assert response.quantity() == 11;

       assert itemRepository.findItemByName("Item update max name").getQuantity() == 11;
    }

    @Test
    void decreseUpdateTestOK(){

        Item item = new Item();
        item.setDesctiption("item update decrease description");
        item.setName("Item update decrease name");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 9;

        var response = itemService.updateQuantity("Item update decrease name", 2, true);

        assert response.quantity() == 7;

        assert itemRepository.findItemByName("Item update decrease name").getQuantity() == 7;
    }

    @Test
    void decreseUpdateTestKO(){

        Item item = new Item();
        item.setDesctiption("item update description ko");
        item.setName("Item update name ko");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 9;

        CustomError response =  assertThrows(CustomError.class, () -> {
            itemService.updateQuantity("", 2, true);
        });
        assert response.getErrTp().equals("Invalid_Request");

    }

    @Test
    void decreseUpdateTestKO_2(){

        Item item = new Item();
        item.setDesctiption("item update description");
        item.setName("Item update name");
        item.setPrice(100.00);
        item.setQuantity(1);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 1;

        var response = itemService.updateQuantity("Item update name", 2, true);

        assert response.quantity() == 1;
        assert response.result().equals("Quantity_too_low");
    }

    @Test
    void itemFilerMaxPriceTestOK(){

        List<Item> request = new ArrayList<>();
        int i = 0;
        var list = List.of(120.00,140.00,60.00,50.00);
        for(var x : List.of("a","b","c","d")){

            Item item = new Item();
            item.setPrice(list.get(i));
            item.setName(x);
            item.setDesctiption(x);
            item.setQuantity(3);

            request.add(item);
            i++;
        }

        itemRepository.saveAll(request);

        var resp = itemService.getItemFilterByMaxPrice(90.00);

        assert resp.stream().filter(y -> y.getName().equals("c")).findAny().get() != null;
        assert resp.stream().filter(y -> y.getName().equals("d")).findAny().get().getPrice() == 50.00;
    }
}
