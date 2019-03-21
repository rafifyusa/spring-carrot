package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.model.Barn;
import com.mitrais.jpqi.springcarrot.service.BarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/barns")
public class BarnController {
    @Autowired
    private BarnService barnService;

    @GetMapping
    public List<Barn> getAllBarns () { return barnService.findAllBarn();}

    @GetMapping("{id}")
    public Barn getBarnById(@PathVariable String id) {return barnService.findBarnById(id);}

    @PostMapping
    public void createBarn(@RequestBody Barn barn) {barnService.createBarn(barn);}

    @DeleteMapping
    public void deleteBarn (@RequestParam String id) {barnService.deleteBarn(id);}

    @PutMapping
    public void updateBarn (@RequestParam String id, @RequestBody Barn barn) {barnService.updateBarn(id,barn);}

    @PatchMapping("/add-awards/{id}")
    public void addAwardsToBarn (@PathVariable String id, @RequestBody List<Award> awards) {
        barnService.addAwardsToBarn(id, awards);
    }

    @PatchMapping("/delete-awards/{id}")
    public void delAwardsFromBarn (@PathVariable String id, @RequestBody Award awards) {
        barnService.deleteAwardsFromBarn(id,awards);
    }
}
