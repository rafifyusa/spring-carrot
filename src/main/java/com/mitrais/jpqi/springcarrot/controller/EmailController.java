package com.mitrais.jpqi.springcarrot.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.mitrais.jpqi.springcarrot.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class EmailController {
    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/sendmail")
    public String sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("lukasyoga.permana@mitrais.com");
            helper.setText("Greetings :) \n You got this email because you request to get all employees");
            helper.setSubject("Mail From Spring Boot");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

    public String sendMailContent(List<String> emailAddress, String subject, String contentBody) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            for(String eAddress: emailAddress){
                System.out.println("eAddress: " + eAddress);
                helper.setTo(eAddress);
                helper.setText("Greetings :) \n" + contentBody);
                helper.setSubject(subject);
                sender.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println( "Error while sending mail ..");
        }
        return "Mail Sent Success!";
    }
}