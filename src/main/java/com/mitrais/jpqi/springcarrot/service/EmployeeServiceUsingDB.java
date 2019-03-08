package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceUsingDB implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;
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
}
