package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Group;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    @Query("{'role': ?0}")
    List<Employee> findByRole(String role);

    @Query("{'spvLevel': ?0}")
    List<Employee> findBySpvLevel(String spvlevel);

    @Query("{'emailAddress': ?0, 'password': ?1}")
    Optional<Employee> findByEmailAddressAndPassword(String email, String password);

    @Query("{'group':{$elemMatch:{$id : ?0}}}")
    List<Employee> findByGroupId(ObjectId id);
}
//    @Query(value = "{ 'userId' : ?0, 'questions.questionID' : ?1 }", fields = "{ 'questions.questionID' : 1 }")
//    List<PracticeQuestion> findByUserIdAndQuestionsQuestionID(int userId, int questionID);