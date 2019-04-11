package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "newsletter")
public class Newsletter {
    @Id
    private String id;
    private String newsletterSubject;
    private String newsletterContent;
    private String senderName; //Admin that login at that time

    public Newsletter() {}

    public Newsletter(String id, String newsletterSubject, String newsletterContent, String senderName) {
        this.id = id;
        this.newsletterSubject = newsletterSubject;
        this.newsletterContent = newsletterContent;
        this.senderName = senderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsletterSubject() {
        return newsletterSubject;
    }

    public void setNewsletterSubject(String newsletterSubject) {
        this.newsletterSubject = newsletterSubject;
    }

    public String getNewsletterContent() {
        return newsletterContent;
    }

    public void setNewsletterContent(String newsletterContent) {
        this.newsletterContent = newsletterContent;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
