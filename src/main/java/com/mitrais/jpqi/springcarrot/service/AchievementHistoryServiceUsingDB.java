package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import com.mitrais.jpqi.springcarrot.repository.AchievementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementHistoryServiceUsingDB implements AchievementHistoryService{

    @Autowired
    AchievementHistoryRepository achievementHistoryRepository;

    public AchievementHistoryServiceUsingDB(AchievementHistoryRepository AchievementHistoryRepository) {
        this.achievementHistoryRepository = AchievementHistoryRepository;
    }

    @Override
    public List<AchievementHistory> getAllAchievementHistory() {
        return achievementHistoryRepository.findAll();
    }

    @Override
    public Iterable<AchievementHistory> findByAchievementHistoryId(String id) {
        return achievementHistoryRepository.findAchievementHistoryById(id);
    }

    @Override
    public List<AchievementHistory> createAchievementHistory(AchievementHistory AchievementHistory) {
        achievementHistoryRepository.save(AchievementHistory);
        return achievementHistoryRepository.findAll();
    }

    @Override
    public List<AchievementHistory> updateAchievementHistory(String id, AchievementHistory AchievementHistory) {
        AchievementHistory.setId(id);
        achievementHistoryRepository.save(AchievementHistory);
        return achievementHistoryRepository.findAll();
    }

    @Override
    public void deleteAchievementHistory(String id) {
        achievementHistoryRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public void partialUpdateAchievementHistory(String id, AchievementHistory achievementHistory) {
        AchievementHistory temp = achievementHistoryRepository.findById(id);
        if(temp != null){
            if(achievementHistory.getEmployee() != null){
                temp.setEmployee(achievementHistory.getEmployee());
            }
            if(achievementHistory.getTimeAchieved() != null){
                temp.setTimeAchieved(achievementHistory.getTimeAchieved());
            }
            if(achievementHistory.getAchievement() != null){
                temp.setAchievement(achievementHistory.getAchievement());
            }
        }
        achievementHistoryRepository.save(temp);

    }
}
