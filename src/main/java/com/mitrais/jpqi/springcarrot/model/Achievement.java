package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

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

    public Achievement(String id, String title, String description, int carrot, Role role, boolean status, String reasoning) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.carrot = carrot;
        this.role = role;
        this.status = status;
        this.reasoning = reasoning;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}