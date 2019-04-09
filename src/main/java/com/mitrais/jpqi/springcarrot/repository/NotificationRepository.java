package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Notification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    @Query("{'owner.$id': ?0, read: ?1}")
    List<Notification> findAllByEmployeeIdAndStatus(ObjectId id, Boolean status);
}
