package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Document("transactions")
public class Transaction {

    public enum Type{
        BAZAAR, REWARD, SHARED, DONATION
    }

    public enum Status{
        PENDING, APPROVED, DECLINED
    }

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String from;
    private String to;
    private Basket detail_from;
    private Basket detail_to;
    private Freezer freezer_from;
    private Item requested_item;
    @DBRef
    private SocialFoundation socialFoundation;
    private String description;
    private int carrot_amt;
    private LocalDateTime transaction_date;
    private Status status;

    public Transaction(){}


    public Transaction(String id, Type type, String from, String to, Basket detail_from,
                       Basket detail_to, Freezer freezer_from, Item requested_item, String description,
                       SocialFoundation socialFoundation, int carrot_amt,
                       LocalDateTime transaction_date, Status status) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.to = to;
        this.detail_from = detail_from;
        this.detail_to = detail_to;
        this.freezer_from = freezer_from;
        this.requested_item = requested_item;
        this.description = description;
        this.socialFoundation = socialFoundation;
        this.carrot_amt = carrot_amt;
        this.transaction_date = transaction_date;
        this.status = status;
    }

    //Getter & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom() { return from; }

    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }

    public void setTo(String to) { this.to = to; }

    public Basket getDetail_from() { return detail_from; }

    public void setDetail_from(Basket detail_from) { this.detail_from = detail_from; }

    public Basket getDetail_to() { return detail_to; }

    public void setDetail_to(Basket detail_to) { this.detail_to = detail_to; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCarrot_amt() {
        return carrot_amt;
    }

    public void setCarrot_amt(int carrot_amt) {
        this.carrot_amt = carrot_amt;
    }

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Freezer getFreezer_from() { return freezer_from; }

    public void setFreezer_from(Freezer freezer_from) { this.freezer_from = freezer_from; }

    public Item getRequested_item() { return requested_item; }

    public void setRequested_item(Item requested_item) { this.requested_item = requested_item; }

    public SocialFoundation getSocialFoundation() { return socialFoundation; }

    public void setSocialFoundation(SocialFoundation socialFoundation) { this.socialFoundation = socialFoundation; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}
