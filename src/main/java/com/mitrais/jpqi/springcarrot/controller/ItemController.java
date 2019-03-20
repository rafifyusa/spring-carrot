package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.service.ItemService;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/items")
public class ItemController  {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Create
    @PostMapping
    public void create(@RequestBody Item item) {
        itemService.createItem(item);
    }

    // Edit or Update
    @PutMapping("{id}")
    public void update(@PathVariable String id, @RequestBody Item item) {
        itemService.updateItem(id, item);
    }

    // Delete
    @DeleteMapping("{id}")
    public void remove(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    // Get all
    @GetMapping
    public List<Item> get() {
        return itemService.getAll();
    }

    @GetMapping("{id}")
    public Item getItem(@PathVariable String id) {
        return itemService.findItemById(id);
    }

    // Partial Update or Patch
    @PatchMapping("{id}")
    public void patch(@PathVariable String id, @RequestBody Item item) {
        itemService.updatePartialItem(id, item);
    }

    // Sorting list by number of exchange rate
    @GetMapping("sortByExchangeRate")
    public List<Item> sortByExchangeRate() {
        return itemService.sortByExchangeRate();
    }

    @GetMapping("findByBazaarId")
    public List<Item> findByBazaarId(@RequestParam String id) {
        return itemService.findAllByBazaarId(id);
    }

    @GetMapping("findByCarrotAmount")
    public List<Item> findByCarrotAmount(@RequestParam int carrotAmount) {
        return itemService.findAllByExchangeRateAmount(carrotAmount);
    }
}
