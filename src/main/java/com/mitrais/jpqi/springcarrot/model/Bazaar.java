package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.Id;
import java.time.LocalDate;

public class Bazaar {

    @Id
    private int id;
    private String bazaarName;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private boolean status;
    @DBRef
    private Employee owner; //Todo ask for advice

    public Bazaar() { }

    public Bazaar(int id, String bazaarName, LocalDate startPeriod, LocalDate endPeriod, Employee owner) {
        this.id = id;
        this.bazaarName = bazaarName;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBazaarName() {
        return bazaarName;
    }

    public void setBazaarName(String bazaarName) {
        this.bazaarName = bazaarName;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
