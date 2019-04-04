package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Item;
import com.mitrais.jpqi.springcarrot.responses.ItemResponse;
import com.mitrais.jpqi.springcarrot.service.ItemService;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> create(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    // Edit or Update
    @PutMapping("{id}")
    public void update(@PathVariable String id, @RequestBody Item item) {
        itemService.updateItem(id, item);
    }

    // Delete
    @DeleteMapping("{id}")
    public ItemResponse remove(@PathVariable String id) {
        return itemService.deleteIgitem(id);
    }

    // Get all
    @GetMapping
    public ItemResponse get() {
        return itemService.getAll();
    }

    @GetMapping("{id}")
    public ItemResponse getItem(@PathVariable String id) {
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

    @PostMapping("findByMultipleBazaarId")
    public List<Item> findByMultipleBazaarId(@RequestBody List<Bazaar> id) {
        return itemService.findAllByMultipleBazaarId(id);
//        return null;
    }

    @GetMapping("findByCarrotAmount")
    public List<Item> findByCarrotAmount(@RequestParam int carrotAmount) {
        return itemService.findAllByExchangeRateAmount(carrotAmount);
    }

    /**
     * Upload Image
     */
    @PostMapping("uploadImage/{id}")
    public void patchUploadImage(@RequestBody Map<String, String> param, @PathVariable String id) {
//        System.out.println("reniii");
        itemService.picturePatch(param.get("img"), id);
    }
}