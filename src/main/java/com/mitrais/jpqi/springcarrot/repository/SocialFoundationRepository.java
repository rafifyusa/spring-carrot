package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialFoundationRepository extends MongoRepository<SocialFoundation, Integer> {
}
