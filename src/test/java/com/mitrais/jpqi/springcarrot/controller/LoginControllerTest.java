package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Carrot;
import com.mitrais.jpqi.springcarrot.responses.Login;
import com.mitrais.jpqi.springcarrot.service.CarrotServiceUsingDB;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LoginControllerTest {
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;
    private String email;
    private String password;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { { "someonenotlikeyou2@rocketmail.com", "password" },
                { "someonenotlikeyou2@rocketmail.com", "password" }});
    }

    public LoginControllerTest(String email, String password) {
        this.email= email;
        this.password = password;
    }

    @Test
    public void contextLoads() {
        Map<String, String> credential = new HashMap<>();
        credential.put("email", this.email);
        credential.put("password", this.password);
        Login hasil = employeeServiceUsingDB.findEmployeeByCredential(credential);
        System.out.println(hasil.getStatus());
        Assertions.assertTrue(hasil.getStatus());
    }
}
