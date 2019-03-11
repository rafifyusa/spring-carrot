package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EmployeeServiceUsingDB implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public EmployeeServiceUsingDB(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    @Override
    public void deleteEmployee(int id) {
        employeeRepository.delete(employeeRepository.findById(id));
    }
    @Override
    public void updateEmployee(int id, Employee employee) {
        employee.setId(id);
        employeeRepository.save(employee);
    }
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> temp = Optional.ofNullable(employeeRepository.findById(id));
        if(temp.isPresent()) {
            return temp.get();
        }
        return null;
    }

    @Override
    public List<GroupCount> getAllEmployeeGroups() {

        Aggregation agg = newAggregation(
                group("group").count().as("total"),
                project("total").and("group").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        );

        //convert to agg result to list
        AggregationResults<GroupCount> groupResult =
                mongoTemplate.aggregate(agg,Employee.class, GroupCount.class);

        List<GroupCount>groups = groupResult.getMappedResults();

        return groups;
    }
}
