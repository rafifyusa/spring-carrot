package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Barn;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BarnRepository extends MongoRepository<Barn, String> {
}
