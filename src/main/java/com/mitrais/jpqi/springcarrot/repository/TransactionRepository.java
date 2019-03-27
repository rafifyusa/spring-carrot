package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    @Query("{'detail_to.employee.$id': ?0}")
    List<Transaction>  findDetailToByEmployeeId (ObjectId id);

    @Query("{'detail_from.employee.$id': ?0}")
    List<Transaction>  findDetailFromByEmployeeId (ObjectId id);

    @Query("{'type' : ?0}")
    List<Transaction> findTransactionByType (ObjectId id);

    @Query("{'requested_item.bazaar.$id': ?0}")
    List<Transaction> findTransactionByBazaarId (ObjectId id);
}
