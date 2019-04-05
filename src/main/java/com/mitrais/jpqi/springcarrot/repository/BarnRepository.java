package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Barn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BarnRepository extends MongoRepository<Barn, String> {
    @Query("{'status': ?0, 'startPeriod':{$gte: ?1, $lte: ?2}}")
    List<Barn> findBarnByStatusAndByStartPeriod(boolean status, LocalDateTime startDate, LocalDateTime endDate);
}
