package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    // Get the
    @Query("{'owner.$id': ?0}")
    List<Group> findGroupIdByOwner(ObjectId ownerId);

    @Query("{'awards':{$elemMatch:{$id: ?0}}}")
    List<Group> findGroupsByAwardsId (ObjectId awardId);
}
