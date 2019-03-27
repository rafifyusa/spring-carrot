package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BazaarRepository extends MongoRepository<Bazaar, String> {
    @Query("{'status': ?0}")
    List<Bazaar> findBazaarByStatus (boolean status);

    @Query("{'owner.$id': ?0}")
    Bazaar findBazaarByOwnerId(ObjectId id);
}
