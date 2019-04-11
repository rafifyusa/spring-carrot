package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Newsletter;
import com.mitrais.jpqi.springcarrot.repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsletterService {
    private NewsletterRepository newsletterRepository;
    private EmployeeService employeeService;
    @Autowired
    private JavaMailSender sender;

    // Constructor
    public NewsletterService(NewsletterRepository newsletterRepository, EmployeeService employeeService) {
        this.newsletterRepository = newsletterRepository;
        this.employeeService = employeeService;
    }

    // Create New newsletter
    public void insertNewsletter(Newsletter input) {
//        String[] to = getAllEmployeeEmail().toArray(new String[0]);
        List<String> to = getAllEmployeeEmail();
        String subject = input.getNewsletterSubject();
        String contentBody = input.getNewsletterContent();

        System.out.println(sendMailContent(to, subject, contentBody));

        newsletterRepository.save(input);
    }

    // Show all newsletter
    public List<Newsletter> getAllNewsLetter() {
//        getAllEmployeeEmail();
        return newsletterRepository.findAll();
    }

    // Save all employee email to list
    private List<String> getAllEmployeeEmail() {
        List<String> employeesEmail = employeeService.getAllEmployee().getListEmployee().stream()
                .map(employee -> employee.getEmailAddress())
                .collect(Collectors.toList());
//        String[] to = employeesEmail.toArray(new String[0]);
//        System.out.println(to);
        return employeesEmail;
    }

    // Create emailing methods
    private String sendMailContent(List<String> emailAddress, String subject, String contentBody) {
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
