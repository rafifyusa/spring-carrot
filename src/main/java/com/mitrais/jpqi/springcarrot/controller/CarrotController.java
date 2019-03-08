package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import com.mitrais.jpqi.springcarrot.service.CarrotService;
import com.mitrais.jpqi.springcarrot.service.CarrotServiceUsingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carrots")
public class CarrotController {
    @Autowired
    CarrotServiceUsingDB carrotServiceUsingDB;

    @GetMapping
    public List<Carrot> getCarrots(){
        List<Carrot>cr =carrotServiceUsingDB.getAllCarrot();
        return cr;
    }

    @PostMapping
    void createCarrots(@RequestBody Carrot carrot) {
        carrotServiceUsingDB.createCarrot(carrot);
    }

    @PatchMapping
    void updateCarrots(@RequestBody Carrot carrot){
        carrotServiceUsingDB.updateCarrot(carrot);
    }

}
