package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.AchievementHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AchievementHistoryRepository extends MongoRepository <AchievementHistory, Integer> {
    @Query("{'id': ?0}")
    List<AchievementHistory> findAchievementHistoryById(String id);
}
