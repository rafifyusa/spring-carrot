package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BazaarRepository extends MongoRepository<Bazaar, Integer> {
    //Query untuk get status by id,untuk change
}
