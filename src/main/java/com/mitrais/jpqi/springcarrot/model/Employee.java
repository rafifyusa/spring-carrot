package com.mitrais.jpqi.springcarrot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String name;
    private LocalDate dob;
    private String address;
    private String password;
    private String profilePicture;
    private String emailAddress;
    @DBRef
    private Set<Group> group;
    @DBRef
    private Employee supervisor;
    @DBRef
    private List<Achievement> achievement;

    @Enumerated(EnumType.STRING)
    public Role role;
    @Enumerated(EnumType.STRING)
    private SpvLevel spvLevel;


    // Constructor
    public Employee() {}

    public Employee(String id, String name, LocalDate dob, String address, Role role, String password,
                    String profilePicture, String emailAddress, Set<Group> group, Employee supervisor,
                    SpvLevel spvLevel, List<Achievement> achievement) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.role = role;
        this.password = password;
        this.profilePicture = profilePicture;
        this.emailAddress = emailAddress;
        this.group = group;
        this.supervisor = supervisor;
        this.spvLevel = spvLevel;
        this.achievement = achievement;
    }

    public List<Achievement> getAchievement() {
        return achievement;
    }

    public void setAchievement(List<Achievement> achievement) {
        this.achievement = achievement;
    }

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

    public Set<Group> getGroup() { return group; }
    public void setGroup(Set<Group> group) { this.group = group; }

    // Enum for roles or position
    public enum Role implements GrantedAuthority {
        STAFF,
        ADMIN,
        UNKNOWN,
        MANAGER,
        EMPLOYEE,
        ROOT_ADMIN,
        STAKEHOLDER,
        SENIOR_MANAGER;
        public String getAuthority() {
            return name();
        }
    }

    public enum SpvLevel{
        None,
        Supervisor1,
        Supervisor2,
        Manager1,
        Manager2
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return id == employee.id;
    }

    public Employee getSupervisor() {return supervisor;}

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public SpvLevel getSpvLevel() {return spvLevel;}

    public void setSpvLevel(SpvLevel spvLevel) {this.spvLevel = spvLevel;}

}