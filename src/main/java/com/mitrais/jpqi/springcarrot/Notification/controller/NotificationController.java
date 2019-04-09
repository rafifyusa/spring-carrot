package com.mitrais.jpqi.springcarrot.Notification.controller;

import com.mitrais.jpqi.springcarrot.Notification.Greeting;
import com.mitrais.jpqi.springcarrot.Notification.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

public class NotificationController {
    @MessageMapping("/hello")
    @SendTo("/topic/chat")
    public Greeting greeting(Notification message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
