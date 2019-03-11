package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AchievementService {
    List<Achievement> getAllAchievement();
    Optional<Achievement> findByAchievementId (int id);
    List<Achievement> findByAchievementRole (String role);
    List<Achievement> createAchievement(Achievement achievement);
    List<Achievement> updateAchievement(int id, Achievement achievement);
    void deleteAchievement(int id);
}
