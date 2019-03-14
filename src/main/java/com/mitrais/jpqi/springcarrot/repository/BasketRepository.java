package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BasketRepository extends MongoRepository<Basket, Integer> {
    @Query("{'employee.$id': ?0}")
    List<Basket> findByEmployee(int id);
}
