package com.mitrais.jpqi.springcarrot.controller;


import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.responses.SocialFoundationResponse;
import com.mitrais.jpqi.springcarrot.service.SocialFoundationServiceUsingDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/socialfoundations")
public class SocialFoundationController {
    private SocialFoundationServiceUsingDB socialFoundationServiceUsingDB;

    public SocialFoundationController(SocialFoundationServiceUsingDB socialFoundationServiceUsingDB){
        this.socialFoundationServiceUsingDB = socialFoundationServiceUsingDB;
    }

    @GetMapping("{id}")
    public SocialFoundationResponse getById(@PathVariable("id") String id){
        return socialFoundationServiceUsingDB.getSocialFoundationById(id);
    }

    @GetMapping
    public SocialFoundationResponse get(){
        return socialFoundationServiceUsingDB.getAllSocialFoundation();
    }

    @DeleteMapping("{id}")
    public SocialFoundationResponse delete(@PathVariable("id") String id){
        return socialFoundationServiceUsingDB.deleteSocialFoundation(id);
    }

    //create new
    @PostMapping
    public SocialFoundationResponse create(@RequestBody SocialFoundation socialFoundation){
        return socialFoundationServiceUsingDB.createSocialFoundation(socialFoundation);
    }

    //update
    @PutMapping("{id}")
    public SocialFoundationResponse update(@PathVariable("id") String id, @RequestBody SocialFoundation socialFoundation){
        return socialFoundationServiceUsingDB.updateSocialFoundation(id, socialFoundation);
    }

    @PatchMapping("{id}")
    public SocialFoundationResponse partialUpdate(@PathVariable("id") String id, @RequestBody SocialFoundation socialFoundation){
        return socialFoundationServiceUsingDB.partialUpdate(id, socialFoundation);
    }

    // Sorted based on Contribution
    @GetMapping("getSorted")
    public List<SocialFoundation> getSortedSocialFoundation() {
        return socialFoundationServiceUsingDB.getMostContributedSocialFoundation();
    }

    @PostMapping("uploadImage/{id}")
    public void uploadImage(@RequestBody Map<String, String> param, @PathVariable String id) {
        socialFoundationServiceUsingDB.picturePatch(param.get("img"), id);
    }
}
