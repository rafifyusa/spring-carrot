package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Set;

@Document("groups")
public class Group {

    public enum Type{
        STAFF, MANAGEMENT
    }

    @Id
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime created_at;
//    private Set<Employee> member;



    //Constructor
    public Group(){};

    public Group(int id, String name, Set<Employee> member, LocalDateTime created_at){
        this.id = id;
        this.name = name;
//        this.member = member;
        this.created_at = created_at;
    }

    //Getters & Setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

//    public Set<Employee> getMember() { return member; }
//
//    public void setMember(Set<Employee> member) { this.member = member; }

    public LocalDateTime getCreated_at() { return created_at; }

    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group that = (Group) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}