package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;

@Document(collection = "employees")
public class Employee {
    @Id
    private int id;
    private String name;
    private LocalDate dob;
    private String address;
    private String password;
    private String profilePicture;
    private String emailAddress;
    private String group;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Default Constructor
    public Employee() {}

    public Employee(int id, String name, LocalDate dob, String address, Role role, String password,
                    String profilePicture, String emailAddress, String group) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.role = role;
        this.password = password;
        this.profilePicture = profilePicture;
        this.emailAddress = emailAddress;
        this.group = group;
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

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
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

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
