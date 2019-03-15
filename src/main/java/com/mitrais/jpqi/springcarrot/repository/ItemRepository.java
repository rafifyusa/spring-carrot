package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    @Query("{'bazaar.$id': '5c8b1a881869272f548094c6' }")
    List<Item> findByBazaar(String id);
}
