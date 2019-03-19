package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.AchievementHistoryRepository;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AchievementHistoryServiceUsingDB implements AchievementHistoryService{

    @Autowired
    AchievementHistoryRepository AchievementHistoryRepository;

    public AchievementHistoryServiceUsingDB(AchievementHistoryRepository AchievementHistoryRepository) {
        this.AchievementHistoryRepository = AchievementHistoryRepository;
    }

    @Override
    public List<AchievementHistory> getAllAchievementHistory() {
        return AchievementHistoryRepository.findAll();
    }

    @Override
    public Iterable<AchievementHistory> findByAchievementHistoryId(String id) {
        return AchievementHistoryRepository.findAchievementHistoryById(id);
    }

    @Override
    public List<AchievementHistory> createAchievementHistory(AchievementHistory AchievementHistory) {
        AchievementHistoryRepository.save(AchievementHistory);
        return AchievementHistoryRepository.findAll();
    }

    @Override
    public List<AchievementHistory> updateAchievementHistory(String id, AchievementHistory AchievementHistory) {
        AchievementHistory.setId(id);
        AchievementHistoryRepository.save(AchievementHistory);
        return AchievementHistoryRepository.findAll();
    }

    @Override
    public void deleteAchievementHistory(String id) {
        AchievementHistoryRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public void partialUpdateAchievementHistory(String id, AchievementHistory AchievementHistory) {

    }
}
