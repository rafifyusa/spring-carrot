package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.service.BasketService;
//import jdk.internal.dynalink.linker.LinkerServices;
import com.mitrais.jpqi.springcarrot.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/basket")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @Autowired
    SequenceService sequenceService;
    @GetMapping
    public List<Basket> findAllBasket(@RequestParam(required = false) String userid) {
        if (userid != null) {
            return basketService.findByEmployee(userid);
        }
        return basketService.findAllBasket();
    }

    @GetMapping("{id}")
    public Basket findBasketById(@PathVariable String id) {
        return basketService.findBasketById(id);
    }

    @GetMapping("sortedEmp")
    public List<Employee> findAllBasketSortedByCarrotAmt(){ return basketService.findEmployeeSortedByCarrotAmt();}

    @PostMapping
    public void insertBasketIntoDB(@RequestBody Basket basket) {
        /*int id = sequenceService.generateSequence(Basket.SEQUENCE_NAME);
        basket.setId(id);*/
        basketService.insertBasketIntoDB(basket);
    }

    @PatchMapping("{id}")
    public void updateBasket(@RequestBody Basket basket, @PathVariable String id) {
        basketService.updateBasketIntoDB(id, basket);
    }

    @DeleteMapping("{id}")
    public void deleteBasket(@PathVariable String id) {basketService.deleteBasketById(id);}


}
