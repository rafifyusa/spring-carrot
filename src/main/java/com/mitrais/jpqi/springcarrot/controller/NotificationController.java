package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Notification;
import com.mitrais.jpqi.springcarrot.responses.NotificationResponse;
import com.mitrais.jpqi.springcarrot.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
@CrossOrigin
public class NotificationController {
    private NotificationService notificationService;
    public NotificationController(NotificationService notificationService){this.notificationService = notificationService;}

    //Basic CRUD
    @GetMapping
    public List<Notification> getAllNotificaiton(){return notificationService.findAllNotif();}

    @PostMapping
    public void createNotification(@RequestBody Notification notification){
        notificationService.createNotif(notification);
    }

    @PutMapping("{id}")
    public void updateNotification(@PathVariable String id, @RequestBody Notification notification){
        notificationService.updateNotif(id, notification);
    }

    @DeleteMapping("{id}")
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotif(id);
    }


    //Functionalities
    @GetMapping("{id}")
    public Notification getNotifById(@PathVariable String id){ return notificationService.findById(id);}

    @GetMapping("emp/{id}")
    public NotificationResponse getAllUnreadNotifByEmpId (@PathVariable String id) {
        return notificationService.findUnreadNotifByEmployeeId(id);
    }

}
