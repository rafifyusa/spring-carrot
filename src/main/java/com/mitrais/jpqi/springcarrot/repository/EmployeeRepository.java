package com.mitrais.jpqi.springcarrot.repository;

import com.mitrais.jpqi.springcarrot.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {
    @Query("{'role': ?0}")
    List<Employee> findByRole(String role);

    @Query("{'group': ?0}")
    List<Employee> findByGroup(String group);
}
