package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.service.BasketService;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/basket")
public class BasketController {
    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<Basket> findAllBasket() {
        return basketService.findAllBasket();
    }

    @GetMapping("{id}")
    public Basket findBasketById(@PathVariable int id) {
        return basketService.findBasketById(id);
    }

    @PostMapping
    public void insertBasketIntoDB(@RequestBody Basket basket) {
        basketService.insertBasketIntoDB(basket);
    }

    @PatchMapping
    public void updateBasket(@RequestBody Basket basket) {
        basketService.updateBasketIntoDB(basket);
    }
}
