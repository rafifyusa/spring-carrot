package com.mitrais.jpqi.springcarrot.controller;


import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.service.SocialFoundationServiceUsingDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/socialfoundations")
public class SocialFoundationController {
    private SocialFoundationServiceUsingDB socialFoundationServiceUsingDB;

    public SocialFoundationController(SocialFoundationServiceUsingDB socialFoundationServiceUsingDB){
        this.socialFoundationServiceUsingDB = socialFoundationServiceUsingDB;
    }

    @GetMapping("{id}")
    public List<SocialFoundation> getById(@PathVariable("id") String id){
        return socialFoundationServiceUsingDB.getSocialFoundationById(id);
    }

    @GetMapping
    public List<SocialFoundation> get(){
        return socialFoundationServiceUsingDB.getAllSocialFoundation();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id){
        socialFoundationServiceUsingDB.deleteSocialFoundation(id);
    }

    //create new
    @PostMapping
    public void create(@RequestBody SocialFoundation socialFoundation){
        socialFoundationServiceUsingDB.createSocialFoundation(socialFoundation);
    }

    //update
    @PutMapping("{id}")
    public void update(@PathVariable("id") String id, @RequestBody SocialFoundation socialFoundation){
        socialFoundationServiceUsingDB.updateSocialFoundation(id, socialFoundation);
    }

    @PatchMapping("{id}")
    public void partialUpdate(@PathVariable("id") String id, @RequestBody SocialFoundation socialFoundation){
        socialFoundationServiceUsingDB.partialUpdate(id, socialFoundation);
    }

}
