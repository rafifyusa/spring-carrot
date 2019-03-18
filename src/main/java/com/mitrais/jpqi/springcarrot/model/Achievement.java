package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "achievement")
public class Achievement {
    @Id
    private String id;
    private String title;
    private String description;
    private int carrot;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean status;
    private String reasoning;
    private LocalDateTime dateAchieved;
    @DBRef
    private Employee employee;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    // Enum for roles or position
    private enum Role {
        STAFF,
        ADMIN,
        UNKNOWN,
        MANAGER,
        EMPLOYEE,
        ROOT_ADMIN,
        STAKEHOLDER,
        SENIOR_MANAGER;
    }

    public Achievement(){}

    public Achievement(String id, String title, String description, int carrot, Role role, boolean status, String reasoning, LocalDateTime dateAchieved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.carrot = carrot;
        this.role = role;
        this.status = status;
        this.reasoning = reasoning;
        this.dateAchieved = dateAchieved;
    }

    public LocalDateTime getDateAchieved() {
        return dateAchieved;
    }

    public void setDateAchieved(LocalDateTime dateAchieved) {
        this.dateAchieved = dateAchieved;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public int getCarrot() {return carrot;}

    public void setCarrot(int carrot) {this.carrot = carrot;}

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}
}