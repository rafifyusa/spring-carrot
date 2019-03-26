package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;

    @PostMapping
    public Map<String, String> findUserByEmailAndPassword (@RequestBody Map<String, String> body) {
        return this.employeeServiceUsingDB.findEmployeeByCredential(body);
    }

}
