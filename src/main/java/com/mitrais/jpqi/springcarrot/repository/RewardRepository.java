package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Reward;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RewardRepository extends MongoRepository<Reward, Integer> {
}
