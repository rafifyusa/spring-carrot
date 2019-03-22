package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.service.BasketService;
//import jdk.internal.dynalink.linker.LinkerServices;
import com.mitrais.jpqi.springcarrot.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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
            List<Basket> basket = new ArrayList<>();
            Optional<Basket> b = basketService.findByEmployee(userid);
            basket.add(b.get());
            return basket;
        }
        return basketService.findAllBasket();
    }

    @GetMapping("{id}")
    public Basket findBasketById(@PathVariable String id) {
        return basketService.findBasketById(id);
    }

    @GetMapping("sortedEmp")
    public List<Employee> findAllBasketSortedByCarrotAmt(){ return basketService.findEmployeeSortedByCarrotAmt();}

    @GetMapping("emp")
    public Basket findBasketByEmployee(@RequestParam String employeeid) {
        /*Optional<Basket> b = basketService.findByEmployee(employeeid);
        return b.get();*/
        System.out.println(employeeid);
        Basket b = basketService.findBasketByEmployeeId(employeeid);
        System.out.println(b.getName());
        return  b;
    }

    @PostMapping
    public void insertBasketIntoDB(@RequestBody Basket basket) {
        basketService.insertBasketIntoDB(basket);
    }

    @PatchMapping("{id}")
    public void updateBasket(@RequestBody Basket basket, @PathVariable String id) {
        basketService.updateBasketIntoDB(id, basket);
    }

    @DeleteMapping("{id}")
    public void deleteBasket(@PathVariable String id) {basketService.deleteBasketById(id);}


}
