package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    @Query("{'bazaars':{$elemMatch:{$id : ?0}}}")
    List<Bazaar> findBazaarsByGroupId(ObjectId id);

    @Query("{'awards':{$elemMatch:{$id : ?0}}}")
    List<Award> findAwardsByGroupId (ObjectId id);

    @Query("{'achievements':{$elemMatch:{$id : ?0}}}")
    List<Achievement> findAchievementsByGroupId (ObjectId id);

}
