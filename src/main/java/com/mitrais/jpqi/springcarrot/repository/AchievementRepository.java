package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AchievementRepository extends MongoRepository<Achievement, Integer> {
    @Query("{'role': ?0}")
    List<Achievement> findByRole(String role);
}