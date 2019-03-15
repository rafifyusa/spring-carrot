package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Carrot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarrotRepository extends MongoRepository<Carrot, String> {
    @Query("{'basket.employee.$id': ?0}")
    List<Carrot> findByEmployeeId(String basket);
}
