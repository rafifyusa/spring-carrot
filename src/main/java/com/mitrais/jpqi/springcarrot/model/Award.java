package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Document("awards")
public class Award {
    public enum Type{ENDYEAR, DATE, COLUMN, COLUMNYEAR}
    //Collection fields
    @Id
    private int id;
    private String type_name;
    private int carrot_amt;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String note;
    private boolean active;

    //Constructors
    public Award(){ }

    public Award(int id, String type_name, int carrot_amt, Type type, String note, boolean active) {
        this.id = id;
        this.type_name = type_name;
        this.carrot_amt = carrot_amt;
        this.type = type;
        this.note = note;
        this.active = active;
    }

    //Getter & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getCarrot_amt() {
        return carrot_amt;
    }

    public void setCarrot_amt(int carrot_amt) {
        this.carrot_amt = carrot_amt;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
}
