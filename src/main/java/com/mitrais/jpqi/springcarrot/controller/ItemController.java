package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // List all item
    @GetMapping
    public List<Item> findAllItem() {
        return itemService.findAllItem();
    }

    // List by Item Id
    @GetMapping("{id}")
    public Item findItemById(@PathVariable int id) {
        return itemService.findItemById(id);
    }

    // List of sorted item by amount
    @GetMapping("sortedItem")
    public List<Item> sortItemByAmount() {
        return itemService.sortByAmount();
    }

    // Create new Item
    @PostMapping
    public void insertItem(@RequestBody Item item) {
        itemService.insertItem(item);
    }

    // Update
    @PutMapping
    public void updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
    }

    // Delete mapping
    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable int id) {
        itemService.deleteItemById(id);
    }
}
