package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Newsletter;
import com.mitrais.jpqi.springcarrot.service.NewsletterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/newsletter")
public class NewsletterController {
    private NewsletterService newsletterService;

    // Constructor
    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping
    public void create(@RequestBody Newsletter newsletter) {
        newsletterService.insertNewsletter(newsletter);
    }

    @GetMapping
    public List<Newsletter> get() {
        return newsletterService.getAllNewsLetter();
    }
}
