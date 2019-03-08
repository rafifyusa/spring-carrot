package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Freezer;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/freezer")
public class FreezerController {
    @Autowired
    FreezerRepository freezerRepository;

    public FreezerController(FreezerRepository freezerRepository){this.freezerRepository = freezerRepository;}

    @GetMapping
    public List<Freezer> get(@RequestParam(required = false) Integer id){
        List<Freezer> fr = freezerRepository.findAll();
        return fr;
    }

    @PostMapping
    public void create(@RequestBody Freezer freezer){
        freezerRepository.save(freezer);
    }
}
