package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.Id;
import java.time.LocalDate;

public class Bazaar {

    @Id
    private String id;
    private String bazaarName;
    private String bazaarDescription;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private boolean status;
    @DBRef
    private Employee owner;

    public Bazaar() { }

    public Bazaar(String id, String bazaarName, String bazaarDescription, LocalDate startPeriod, LocalDate endPeriod, boolean status, Employee owner) {
        this.id = id;
        this.bazaarName = bazaarName;
        this.bazaarDescription = bazaarDescription;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.status = status;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBazaarDescription() {
        return bazaarDescription;
    }

    public void setBazaarDescription(String bazaarDescription) {
        this.bazaarDescription = bazaarDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bazaar bazaar = (Bazaar) o;

        return id.equals(bazaar.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
