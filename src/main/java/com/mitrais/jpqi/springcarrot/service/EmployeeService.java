package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {
    void createEmployee(Employee employee);
    void deleteEmployee(String id );
    void updateEmployee(String id, Employee employee);
    List<Employee> getAllEmployee();
    Employee getEmployeeById(String id);
    List<GroupCount> getAllEmployeeGroups();
    Map<String, String> findEmployeeByCredential (Map<String, String> body);
    List<Employee> getRecentDOB();

    void partialUpdateEmployee(String id, Employee employee);
    List<Employee> getStaffRole(String role);

    List<Employee> getEmployeeByGroup(String group);

    List<Employee> getEmployeeBySpvLevel(String spvlevel);

    void deleteEmployeeGroup(String id, Group group);
}
