package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document("rewards")
public class Reward {
    @Id
    private String id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private double carrot_amt;
    private boolean active;
    private LocalDate expired_date;
    private String note;

    public Reward(){}

    public Reward(String id, String name, LocalDateTime created_at, LocalDateTime updated_at, boolean active,
                  LocalDate expired_date, String note) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.active = active;
        this.expired_date = expired_date;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(LocalDate expired_date) {
        this.expired_date = expired_date;
    }

    public double getCarrot_amt() {
        return carrot_amt;
    }

    public void setCarrot_amt(double carrot_amt) { this.carrot_amt = carrot_amt; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
