package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private EmployeeServiceUsingDB employeeServiceUsingDB;

    public LoginController(EmployeeServiceUsingDB employeeServiceUsingDB) {
        this.employeeServiceUsingDB = employeeServiceUsingDB;
    }

    @PostMapping
    public Map<String, String> findUserByEmailAndPassword (@RequestBody Map<String, String> body) {
        return employeeServiceUsingDB.findEmployeeByCredential(body);
    }
}
