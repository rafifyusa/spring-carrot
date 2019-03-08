package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    @Autowired
    EmployeeServiceUsingDB employeeServiceUsingDB;
    public EmployeeController(EmployeeServiceUsingDB employeeServiceUsingDB) {
        this.employeeServiceUsingDB = employeeServiceUsingDB;
    }

    @GetMapping
    public void get () {
        System.out.println("aaaa");
    }

    @PostMapping
    public void create(@RequestBody  Employee employee) {
        employeeServiceUsingDB.create(employee);
    }
}
