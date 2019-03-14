package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {
    @Query("{'role': ?0}")
    List<Employee> findByRole(String role);

    @Query("{'group.name': ?0}")
    List<Employee> findByGroupName(String groupname);
}
//    @Query(value = "{ 'userId' : ?0, 'questions.questionID' : ?1 }", fields = "{ 'questions.questionID' : 1 }")
//    List<PracticeQuestion> findByUserIdAndQuestionsQuestionID(int userId, int questionID);