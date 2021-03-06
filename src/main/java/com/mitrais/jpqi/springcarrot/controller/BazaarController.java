package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import com.mitrais.jpqi.springcarrot.service.BazaarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/bazaar")
public class BazaarController {

    @Autowired
    private BazaarService bazaarService;

    public BazaarController(BazaarService bazaarService) {
        this.bazaarService = bazaarService;
    }

    // Create
    @PostMapping
    public void create(@RequestBody Bazaar bazaar) {
        bazaarService.createBazaar(bazaar);
    }

    // Update
    @PutMapping("{id}")
    public void update(@PathVariable("id") String id, @RequestBody Bazaar bazaar) {
        bazaarService.updateBazaar(id, bazaar);
    }
    // Change bazaar status
    @PatchMapping("/{id}")
    public void changeActivation(@PathVariable("id") String id) {
        bazaarService.changeBazaarStatus(id);
    }

    // Show all
    @GetMapping
    public List<Bazaar> getAll() {
        return bazaarService.findAllBazaar();
    }

    // Show by Id
    @GetMapping ("/{id}")
    public Bazaar getById(@PathVariable String id) {
        return bazaarService.findById(id);
    }

    //Show bazaar by status
    @GetMapping ("status")
    public List<Bazaar> getByStatus (@RequestParam boolean status) {
        return bazaarService.findByStatus(status);
    }

    @GetMapping ("bazar-by-owner/{id}")
    public List<Bazaar> getByStatus (@PathVariable String id) {
        return bazaarService.findByOwnerId(id);
    }
}
