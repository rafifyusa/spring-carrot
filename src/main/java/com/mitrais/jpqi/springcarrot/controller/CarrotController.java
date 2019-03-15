package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
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
        System.out.println(data.toString());
        if (data.get("userid") != null) {
            return carrotServiceUsingDB.findByEmployeeId(data.get("userid"));
        }
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
