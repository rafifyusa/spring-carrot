package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    private EmployeeServiceUsingDB employeeServiceUsingDB;

    public EmployeeController(EmployeeServiceUsingDB employeeServiceUsingDB) {
        this.employeeServiceUsingDB = employeeServiceUsingDB;
    }

    // Create new
    @PostMapping
    public void create(@RequestBody Employee employee) {
        employeeServiceUsingDB.createEmployee(employee);
    }

    // Update
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

    // Get employee group
    @GetMapping("groups")
    public List<GroupCount> getGroups() {
        return employeeServiceUsingDB.getAllEmployeeGroups();
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        employeeServiceUsingDB.deleteEmployee(id);
    }

    // Patch
    @PatchMapping("/{id}")
    public void partialUpdate(@PathVariable("id") int id, @RequestBody Employee employee) {
        employeeServiceUsingDB.partialUpdateEmployee(id, employee);
    }

    // Get birthday list of all employee
    @GetMapping("role")
    public List<Employee> getBirthday(@RequestParam String role) {
        return employeeServiceUsingDB.getStaffRole(role);
    }

    @GetMapping("recentdob")
    public List<Employee> getByRecentDOB() {
        return employeeServiceUsingDB.getRecentDOB();
    }

}
