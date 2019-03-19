package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import com.mitrais.jpqi.springcarrot.service.AchievementHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/achievementhistory")
public class AchievementHistoryController {
    private AchievementHistoryService achievementHistoryService;

    public AchievementHistoryController(com.mitrais.jpqi.springcarrot.service.AchievementHistoryService achievementHistoryService) {
        this.achievementHistoryService = achievementHistoryService;
    }

    @GetMapping
    public List<AchievementHistory> getAllAchievementHistory(){
        return achievementHistoryService.getAllAchievementHistory();
    }

    @GetMapping("{id}")
    public Iterable<AchievementHistory> findByAchievementHistoryId(@PathVariable String id){
        return achievementHistoryService.findByAchievementHistoryId(id);
    }

    @PostMapping
    public void create(@RequestBody AchievementHistory AchievementHistory){
        achievementHistoryService.createAchievementHistory(AchievementHistory);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id){
        achievementHistoryService.deleteAchievementHistory(id);
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") String id, @RequestBody AchievementHistory AchievementHistory){
        achievementHistoryService.updateAchievementHistory(id, AchievementHistory);
    }

    @PatchMapping("{id}")
    public void partialUpdate(@PathVariable("id") String id, @RequestBody AchievementHistory achievementHistory){
        achievementHistoryService.partialUpdateAchievementHistory(id, achievementHistory);
    }
}
