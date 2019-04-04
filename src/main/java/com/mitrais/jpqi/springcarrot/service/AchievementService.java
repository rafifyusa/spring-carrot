package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.responses.AchievementResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AchievementService {
    AchievementResponse getAllAchievement();
    Optional<Achievement> findByAchievementId (String id);
    List<Achievement> findByAchievementRole (String role);
    List<Achievement> createAchievement(Achievement achievement);
    List<Achievement> updateAchievement(String id, Achievement achievement);
    void deleteAchievement(String id);
    AchievementResponse partialUpdateAchievement(String id, Achievement achievement);
}
