package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    void createEmployee(Employee employee);
    void deleteEmployee(int id );
    void updateEmployee(int id, Employee employee);
    void getAllEmployee();
    void getEmployeeById(int id);
}
