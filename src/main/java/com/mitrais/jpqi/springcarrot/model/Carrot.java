package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "carrots")
public class Carrot {
    public enum Type{
        NORMAL, FROZEN
    }
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @DBRef
    private Basket basket;

    public Carrot() {}

    public Carrot(String id, Type type, LocalDateTime created_at,
                  LocalDateTime updated_at, Basket basket) {
        this.id = id;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.basket = basket;
    }

    //Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return created_at;}

    public void setCreatedAt(LocalDateTime createdAt) { this.created_at = createdAt; }

    public LocalDateTime getUpdatedAt() { return updated_at; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updated_at = updatedAt; }

    public Basket getBasket() { return basket; }

    public void setBasket(Basket basket) { this.basket = basket; }
}
