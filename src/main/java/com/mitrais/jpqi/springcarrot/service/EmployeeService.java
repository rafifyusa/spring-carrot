package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.responses.BasketResponse;
import com.mitrais.jpqi.springcarrot.responses.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {
    EmployeeResponse createEmployee(Employee employee);
    EmployeeResponse deleteEmployee(String id );
    EmployeeResponse updateEmployee(String id, Employee employee);
    EmployeeResponse getAllEmployee();
    EmployeeResponse getEmployeeById(String id);
    List<GroupCount> getAllEmployeeGroups();
    Map<String, String> findEmployeeByCredential (Map<String, String> body);
    BasketResponse getRecentDOB();

    EmployeeResponse partialUpdateEmployee(String id, Employee employee);
    EmployeeResponse getStaffRole(String role);

    List<Employee> getEmployeeBySpvLevel(String spvlevel);

    EmployeeResponse deleteEmployeeGroup(String id, Group group);
}
