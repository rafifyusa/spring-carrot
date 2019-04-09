package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Basket;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends MongoRepository<Basket, String> {
    @Query("{'employee.$id': ?0}")
    Optional<Basket> findByEmployee(ObjectId id);

    @Query("{'employee.$id': ?0}")
    Basket findBasketByEmployeeId (ObjectId id);
}
