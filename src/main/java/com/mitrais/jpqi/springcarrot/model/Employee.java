package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;

@Document(collection = "employees")
public class Employee {
    @Id
    private int id;
    private String name;
    private Date dob;
    private String address;
    private String password;
    private String profilePicture;
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private Roles role;

    // Enum for roles or position
    private enum Roles{
        STAFF, ADMIN, UNKNOWN, MANAGER, EMPLOYEE, ROOT_ADMIN, STAKEHOLDER, SENIOR_MANAGER;
    }

    // Default Constructor
    public Employee() {}

    public Employee(int id, String name, Date dob, String address, Roles role, String password,
                    String profilePicture, String emailAddress) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.role = role;
        this.password = password;
        this.profilePicture = profilePicture;
        this.emailAddress = emailAddress;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
