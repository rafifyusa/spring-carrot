package com.mitrais.jpqi.springcarrot.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate template;
    public void onReceivedMesage(String message){
        System.out.println("scheduled");
        this.template.convertAndSend("topic/chat",  new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);
    }
}
