package com.mitrais.jpqi.springcarrot.controller;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import com.mitrais.jpqi.springcarrot.responses.CarrotResponse;
import com.mitrais.jpqi.springcarrot.responses.Response;
import com.mitrais.jpqi.springcarrot.service.CarrotService;
import com.mitrais.jpqi.springcarrot.service.CarrotServiceUsingDB;
//import javafx.scene.layout.BackgroundImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/carrots")
public class CarrotController {
    @Autowired
    CarrotServiceUsingDB carrotServiceUsingDB;

    @GetMapping
    public List<Carrot> getCarrots(@RequestParam(required = false) Map<String, String> data){
        if (data.get("basketid") != null) {
            return carrotServiceUsingDB.findByBasketId(data.get("basketid"));
        }
        List<Carrot>cr =carrotServiceUsingDB.getAllCarrot();
        return cr;
    }

    @PostMapping
    public CarrotResponse createCarrots(@RequestBody Carrot carrot) {
        return carrotServiceUsingDB.createCarrot(carrot);
    }

    @PatchMapping
    void updateCarrots(@RequestBody Carrot carrot){
        carrotServiceUsingDB.updateCarrot(carrot);
    }

    @GetMapping("fresh-by-barn")
    public List<Carrot> getFreshCarrotsByBarnId (@RequestParam String barnId){
        return carrotServiceUsingDB.findFreshCarrotByBarnId(barnId);}

}
