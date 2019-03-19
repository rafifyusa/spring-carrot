package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import com.mitrais.jpqi.springcarrot.service.AchievementHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/achievementhistory")
public class AchievementHistoryController {
    private AchievementHistoryService AchievementHistoryService;

    public AchievementHistoryController(com.mitrais.jpqi.springcarrot.service.AchievementHistoryService achievementHistoryService) {
        AchievementHistoryService = achievementHistoryService;
    }

    @GetMapping
    public List<AchievementHistory> getAllAchievementHistory(){
        return AchievementHistoryService.getAllAchievementHistory();
    }

    @GetMapping("{id}")
    public Iterable<AchievementHistory> findByAchievementHistoryId(@PathVariable String id){
        return AchievementHistoryService.findByAchievementHistoryId(id);
    }

    @PostMapping
    public void create(@RequestBody AchievementHistory AchievementHistory){
        AchievementHistoryService.createAchievementHistory(AchievementHistory);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id){
        AchievementHistoryService.deleteAchievementHistory(id);
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") String id, @RequestBody AchievementHistory AchievementHistory){
        AchievementHistoryService.updateAchievementHistory(id, AchievementHistory);
    }
}
