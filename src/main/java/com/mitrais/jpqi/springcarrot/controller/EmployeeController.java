package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    private EmployeeServiceUsingDB employeeServiceUsingDB;

    public EmployeeController(EmployeeServiceUsingDB employeeServiceUsingDB) {
        this.employeeServiceUsingDB = employeeServiceUsingDB;
    }

    @PostMapping
    public void create(@RequestBody Employee employee) {
        employeeServiceUsingDB.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody Employee employee) {
        employeeServiceUsingDB.updateEmployee(id, employee);
    }

    // Get All
    @GetMapping
    public List<Employee> get() {
        return employeeServiceUsingDB.getAllEmployee();
    }

    // Get by Id
    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") int id) {
        return employeeServiceUsingDB.getEmployeeById(id);
    }

    @GetMapping("groups")
    public List<GroupCount> getGroups() {
        return employeeServiceUsingDB.getAllEmployeeGroups();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        employeeServiceUsingDB.deleteEmployee(id);
    }

    @PatchMapping("/{id}")
    public void partialUpdate(@PathVariable("id") int id, @RequestBody Employee employee) {
        employeeServiceUsingDB.partialUpdateEmployee(id, employee);
    }

//    @GetMapping("recentdob")
//    public List<Employee> get()
}
