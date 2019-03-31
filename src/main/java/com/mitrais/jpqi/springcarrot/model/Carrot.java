package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Document(collection = "carrots")
public class Carrot {
    public enum Type{
        NORMAL, FROZEN, INACTIVE, FRESH
    }
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @DBRef
    private Basket basket;
    @DBRef
    private Freezer freezer;
    @DBRef
    private Barn barn;
    private boolean usable = true;

    public Carrot() {}

    public Carrot(String id, Type type, LocalDateTime created_at,
                  LocalDateTime updated_at, Basket basket, Freezer freezer, Barn barn,boolean usable) {
        this.id = id;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.basket = basket;
        this.freezer = freezer;
        this.barn = barn;
        this.usable = usable;

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

    public Basket getBasket() { return basket; }

    public void setBasket(Basket basket) { this.basket = basket; }

    public LocalDateTime getCreated_at() { return created_at; }

    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getUpdated_at() { return updated_at; }

    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }

    public Freezer getFreezer() { return freezer; }

    public void setFreezer(Freezer freezer) { this.freezer = freezer; }

    public Barn getBarn() { return barn; }

    public void setBarn(Barn barn) { this.barn = barn; }

    public boolean isUsable() { return usable; }

    public void setUsable(boolean usable) { this.usable = usable; }
}
