package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Award;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AwardRepository extends MongoRepository<Award, String> {
    @Query("{'date': {$gte: ?0, $lte: ?1}, 'active':?2}")
    List<Award> findAwardsByDateEqualsAndActive(LocalDate date, LocalDate nextDate, boolean status);

    @Query("{'date': ?0}")
    List<Award> findAwardsByDate(LocalDate date);
}
