package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementServiceUsingDB implements AchievementService{
    @Autowired
    AchievementRepository achievementRepository;

    public AchievementServiceUsingDB(AchievementRepository achievementRepository){ this.achievementRepository = achievementRepository;}

    @Override
    public List<Achievement> getAllAchievement() {
        return achievementRepository.findAll();
    }

    @Override
    public Optional<Achievement> findByAchievementId(int id) {
        return achievementRepository.findById(id);
    }

    @Override
    public List<Achievement> findByAchievementRole(String role) {
        return achievementRepository.findByRole(role);
    }

    @Override
    public List<Achievement> createAchievement(Achievement achievement) {
        achievementRepository.save(achievement);
        return achievementRepository.findAll();
    }

    @Override
    public List<Achievement> updateAchievement(int id, Achievement achievement) {
        achievement.setId(id);
        achievementRepository.save(achievement);
        return achievementRepository.findAll();
    }

    @Override
    public void deleteAchievement(int id) {
        achievementRepository.deleteById(id);
    }

    @Override
    public void partialUpdateAchievement(int id, Achievement achievement) {
        Achievement temp = achievementRepository.findById(id).orElse(null);

        if (temp != null){
            if (achievement.getId() != 0){
                temp.setId(achievement.getId());
            }
            if (achievement.getTitle() != null){
                temp.setTitle(achievement.getTitle());
            }
            if (achievement.getDescription() != null){
                temp.setDescription(achievement.getDescription());
            }
            if (achievement.getCarrot() != 0){
                temp.setCarrot(achievement.getCarrot());
            }
            if (achievement.getRole() != null){
                temp.setRole(achievement.getRole());
            }
            if (achievement.isStatus() || !achievement.isStatus()){
                temp.setStatus(achievement.isStatus());
            }
            if (achievement.getReasoning() != null){
                temp.setReasoning(achievement.getReasoning());
            }
        }
        achievementRepository.save(temp);
    }
}
