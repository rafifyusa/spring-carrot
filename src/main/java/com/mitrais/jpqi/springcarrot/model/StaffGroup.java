package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document("staff_group")
public class StaffGroup {
    @Id
    private int id;
    private String name;
    private Set<Employee> member;
    private LocalDateTime created_at;


    //Constructor
    public StaffGroup(){};

    public StaffGroup(int id, String name, Set<Employee> member, LocalDateTime created_at){
        this.id = id;
        this.name = name;
        this.member = member;
        this.created_at = created_at;
    }

    //Getters & Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Employee> getMember() { return member; }

    public void setMember(Set<Employee> member) { this.member = member; }

    public LocalDateTime getCreated_at() { return created_at; }

    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}
