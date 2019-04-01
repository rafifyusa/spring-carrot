package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Freezer;
import com.mitrais.jpqi.springcarrot.service.FreezerService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FreezerRepository extends MongoRepository<Freezer, String> {
    @Query("{'employee.$id': ?0}")
    Freezer findByOwner(ObjectId id);
}
