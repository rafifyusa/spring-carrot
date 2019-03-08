package com.mitrais.jpqi.springcarrot.model;

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
    private int id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Basket basket;

    public Carrot() {}

    public Carrot(int id, Type type, LocalDateTime createdAt,
                  LocalDateTime updatedAt, Basket basket) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.basket = basket;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Basket getBasket() { return basket; }

    public void setBasket(Basket basket) { this.basket = basket; }
}
