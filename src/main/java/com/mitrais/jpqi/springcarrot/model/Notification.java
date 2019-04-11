package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("notifications")
public class Notification {
    @Id
    private String id;
    private String detail;
    private String type = "";
    private boolean show = true;
    @DBRef
    private Employee owner;
    private boolean read;

    public  Notification(){};
    public Notification(String id, String detail, Employee owner, boolean read) {
        this.id = id;
        this.detail = detail;
        this.owner = owner;
        this.read = read;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public Employee getOwner() { return owner; }

    public void setOwner(Employee owner) { this.owner = owner; }

    public boolean isRead() { return read; }

    public void setRead(boolean read) { this.read = read; }
}
