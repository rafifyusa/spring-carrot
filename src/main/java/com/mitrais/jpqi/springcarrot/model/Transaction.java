package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Document("transactions")
public class Transaction {

    public enum Type{
        BAZAAR, REWARD, SHARED
    }

    @Id
    private long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String from_to;
    private String description;
    private int carrot_amt;
    private LocalDateTime transaction_date;



    public Transaction(){}

    public Transaction(long id, Type type, String from_to, String description,
                       int carrot_amt, LocalDateTime transaction_date) {
        this.id = id;
        this.type = type;
        this.from_to = from_to;
        this.description = description;
        this.carrot_amt = carrot_amt;
        this.transaction_date = transaction_date;
    }

    //Getter & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom_to() {
        return from_to;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }

    public String getDescription() {
        return description;
    }

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
}
