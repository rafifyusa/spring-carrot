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
    @Enumerated(EnumType.STRING)
    private String role;
    private enum roles{
        STAFF, ADMIN, UNKNOWN, MANAGER, EMPLOYEE, ROOT_ADMIN, STAKEHOLDER, SENIOR_MANAGER;
    }
    public Employee() {}
    public Employee(int id, String name, Date dob, String address, String role) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.role = role;
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (dob != null ? !dob.equals(employee.dob) : employee.dob != null) return false;
        if (address != null ? !address.equals(employee.address) : employee.address != null) return false;
        return role != null ? role.equals(employee.role) : employee.role == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}