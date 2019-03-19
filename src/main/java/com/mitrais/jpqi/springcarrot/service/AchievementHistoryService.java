package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AchievementHistoryService {
    List<AchievementHistory> getAllAchievementHistory();
    Iterable<AchievementHistory> findByAchievementHistoryId (String id);
    List<AchievementHistory> createAchievementHistory(AchievementHistory AchievementHistory);
    List<AchievementHistory> updateAchievementHistory(String id, AchievementHistory AchievementHistory);
    void deleteAchievementHistory(String id);
    void partialUpdateAchievementHistory(String id, AchievementHistory AchievementHistory);
}
