package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.responses.AchievementResponse;
import com.mitrais.jpqi.springcarrot.service.AchievementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/achievement")
public class AchievementController {
    private AchievementService achievementService;

    public AchievementController(AchievementService achievementService){this.achievementService = achievementService;}

    @GetMapping
    public AchievementResponse getAllAchievement(){
        return achievementService.getAllAchievement();
    }

    @GetMapping("{id}")
    public Optional<Achievement> findByAchievementId(@PathVariable String id){
        return achievementService.findByAchievementId(id);
    }

    @GetMapping("role")
    public List<Achievement> findByAchievementRole(@RequestParam String role){
        return achievementService.findByAchievementRole(role);
    }

    @PostMapping
    public void create(@RequestBody Achievement achievement){
        achievementService.createAchievement(achievement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id){
        achievementService.deleteAchievement(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Achievement achievement){
        achievementService.updateAchievement(id, achievement);
    }

    @PatchMapping("{id}")
    public AchievementResponse partialUpdate(@PathVariable("id") String id, @RequestBody Achievement achievement){
        return achievementService.partialUpdateAchievement(id, achievement);
    }

}
