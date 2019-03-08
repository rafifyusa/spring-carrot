package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Freezer;
import com.mitrais.jpqi.springcarrot.service.FreezerService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FreezerRepository extends MongoRepository<Freezer, Integer> {

}
