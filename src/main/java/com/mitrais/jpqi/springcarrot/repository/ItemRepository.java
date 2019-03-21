package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    @Query("{'bazaar.$id': ?0 }")
    List<Item> findByBazaar(ObjectId id);
    @Query("{'bazaar.$id': {$in : ?0}}")
    List<Item> findByMultipleBazaar(ObjectId[] id);
}
