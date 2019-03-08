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
    public List<Carrot> getAll(){
        List<Carrot>cr =carrotServiceUsingDB.getAll();
        return cr;
    }

    @PostMapping
    public void create(@RequestBody Carrot carrot) {
        carrotServiceUsingDB.create(carrot);
    }
}
