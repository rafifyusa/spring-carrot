package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Award;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AwardRepository extends MongoRepository<Award, String> {
}
