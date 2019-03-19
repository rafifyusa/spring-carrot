package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AchievementService {
    List<Achievement> getAllAchievement();
    Optional<Achievement> findByAchievementId (String id);
    List<Achievement> findByAchievementRole (String role);
    List<Achievement> createAchievement(Achievement achievement);
    List<Achievement> updateAchievement(String id, Achievement achievement);
    void deleteAchievement(String id);
    void partialUpdateAchievement(String id, Achievement achievement);
}
