package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.notifications.Notification;
import com.mitrais.jpqi.springcarrot.responses.Login;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;
    @Autowired
    private SimpMessagingTemplate template;
    @PostMapping
    public Login findUserByEmailAndPassword (@RequestBody Map<String, String> body) {
        Notification n = new Notification();
        n.setFrom("rafif");
        n.setRole("admin");
        n.setText("halooh reni");
        this.template.convertAndSend("/topic/reply", n);
        return this.employeeServiceUsingDB.findEmployeeByCredential(body);
    }

}
