package com.kafka.demodb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kafka.demodb.BaseDbTest;
import com.kafka.demodb.exception.DbErrorHandler;
import com.kafka.demodb.model.entity.Item;
import com.kafka.demodb.model.request.ItemRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.ItemCrudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest extends BaseDbTest {

    @Autowired
    MockMvc mvc;

    @Test
    void addItemTestOK() throws Exception{

        ItemRequest request = new ItemRequest();
        request.setDesctiption("item description");
        request.setName("Item name");
        request.setPrice(100.00);
        request.setQuantity(9);

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, BaseDbResponse.class);

        assert resp.getResult().equals("OK-00");

        assert itemRepository.findItemByName("Item name").getPrice().equals(100.00);
    }

    @Test
    void addItemTestKO() throws Exception{

        ItemRequest request = new ItemRequest();
        request.setDesctiption("item description");

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        var resp = mapper.readValue(response, DbErrorHandler.CustomExceptionResponse.class);

        assert resp.errTp().equals("Invalid_Request");
    }

    @Test
    void updateItemMaxTestOK() throws Exception{

        Item item = new Item();
        item.setDesctiption("item update max description");
        item.setName("Item update max name");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 9;

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/update/quantity?name=Item update max name&quantity=2&mod=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, ItemCrudService.UpdateQuantitiSummary.class);

        assert resp1.quantity() == 11;

        assert itemRepository.findItemByName("Item update max name").getQuantity() == 11;
    }

    @Test
    void decreseUpdateTestOK() throws Exception {

        Item item = new Item();
        item.setDesctiption("item update decrease description");
        item.setName("Item update decrease name");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 9;

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/update/quantity?name=Item update decrease name&quantity=2&mod=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, ItemCrudService.UpdateQuantitiSummary.class);

        assert resp1.quantity() == 7;

        assert itemRepository.findItemByName("Item update decrease name").getQuantity() == 7;
    }

    @Test
    void decreseUpdateTestKO() throws Exception{

        Item item = new Item();
        item.setDesctiption("item update description ko");
        item.setName("Item update name ko");
        item.setPrice(100.00);
        item.setQuantity(9);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 9;

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/update/quantity?name= &quantity=2&mod=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
    }

    @Test
    void decreseUpdateTestKO_2() throws Exception {

        Item item = new Item();
        item.setDesctiption("item update description");
        item.setName("Item update name");
        item.setPrice(100.00);
        item.setQuantity(1);
        item.setId(12);

        var resp = itemRepository.save(item);

        assert resp != null;
        assert resp.getQuantity() == 1;

        var response = mvc.perform(MockMvcRequestBuilders.post("/api/v1/item/update/quantity?name=Item update name&quantity=2&mod=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, ItemCrudService.UpdateQuantitiSummary.class);

        assert resp1.quantity() == 1;
        assert resp1.result().equals("Quantity_too_low");
    }

    @Test
    void itemFilerMaxPriceTestOK() throws Exception{

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

        var response = mvc.perform(MockMvcRequestBuilders.get("/api/v1/item/filter/maxprice/90.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var resp1 = mapper.readValue(response, new TypeReference<List<Item>>() {
        });

        var resp = itemService.getItemFilterByMaxPrice(90.00);

        assert resp1.stream().filter(y -> y.getName().equals("c")).findAny().get() != null;
        assert resp1.stream().filter(y -> y.getName().equals("d")).findAny().get().getPrice() == 50.00;
    }
}
