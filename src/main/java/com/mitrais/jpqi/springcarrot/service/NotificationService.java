package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Notification;
import com.mitrais.jpqi.springcarrot.repository.NotificationRepository;
import com.mitrais.jpqi.springcarrot.responses.NotificationResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository){ this.notificationRepository = notificationRepository;}

    //Basic CRUD
    public void createNotif (Notification notification){
        notificationRepository.save(notification);
    }

    public List<Notification> findAllNotif() { return notificationRepository.findAll(); }

    public void updateNotif(String id, Notification notification) {
        notification.setId(id);
        notificationRepository.save(notification);
    }

    public void deleteNotif (String id) {notificationRepository.deleteById(id);}

    public Notification findById(String id){
        if (notificationRepository.findById(id).isPresent()){
            return notificationRepository.findById(id).get();
        }
        else {return null;}
    }


    //Functionalities
    public NotificationResponse findUnreadNotifByEmployeeId(String id) {
        NotificationResponse res = new NotificationResponse();
        List<Notification> list =  notificationRepository.findAllByEmployeeIdAndStatus(new ObjectId(id), false);
        res.setStatus(true);
        res.setMessage("List of Notification");
        res.setListNotification(list);
        return res;
    }

}
