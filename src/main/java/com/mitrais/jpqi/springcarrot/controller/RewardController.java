package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Reward;
import com.mitrais.jpqi.springcarrot.service.BasketService;
import com.mitrais.jpqi.springcarrot.service.RewardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rewards")
public class RewardController {
    private RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public List<Reward> findAllReward() {
        return rewardService.findAllReward();
    }

    @GetMapping("{id}")
    public Reward findRewardById(@PathVariable int id) {
        return rewardService.findRewardById(id);
    }

    @PostMapping
    public void insertRewardIntoDB(@RequestBody Reward reward) {
        rewardService.insertRewardIntoDB(reward);
    }

    @PatchMapping
    public void updateReward(@RequestBody Reward reward) {
        rewardService.updateRewardIntoDB(reward);
    }
}
