package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Freezer;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/freezer")
public class    FreezerController {
    @Autowired
    FreezerRepository freezerRepository;

    @GetMapping
    public List<Freezer> get(@RequestParam(required = false) String id){
        if(id != null){
            List<Freezer> fr = (List<Freezer>) freezerRepository.findAllById(Collections.singleton(id));
            return fr;
        }
        List<Freezer> fr = freezerRepository.findAll();
        return fr;
    }

    @GetMapping("by-owner/{id}")
    public Freezer findFrezeerByOwner(@PathVariable String id) {
        return freezerRepository.findByOwner(new ObjectId(id));
    }

    @PostMapping
    public void create(@RequestBody Freezer freezer){
        freezerRepository.save(freezer);
    }
}
