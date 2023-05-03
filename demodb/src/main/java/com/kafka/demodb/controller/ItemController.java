package com.kafka.demodb.controller;

import com.kafka.demodb.model.entity.Item;
import com.kafka.demodb.model.request.ItemRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.ItemService;
import com.kafka.demodb.service.internal.ItemCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {


    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("add")
    public ResponseEntity<BaseDbResponse> addItem(@RequestBody ItemRequest request){
        return ResponseEntity.ok(itemService.addItem(request));
    }

    @PostMapping("getItem")
    public ResponseEntity<ItemService.GetItemResponse> getItem(@RequestBody ItemRequest request){
        return ResponseEntity.ok(itemService.getItem(request));
    }

    @PostMapping("update/quantity")
    public ResponseEntity<ItemCrudService.UpdateQuantitiSummary> updateQuantity(@RequestParam ("name") String name,
                                                                                @RequestParam ("quantity") long quantity,
                                                                                @RequestParam ("mod") boolean mod){
        return ResponseEntity.ok(itemService.updateQuantity(name,quantity,mod));
    }

    @GetMapping("filter/maxprice/{price}")
    public ResponseEntity<List<Item>> filteredItem(@PathVariable ("price") Double price){
        return ResponseEntity.ok(itemService.getItemFilterByMaxPrice(price));
    }
}
