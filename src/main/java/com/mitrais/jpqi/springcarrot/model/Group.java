package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document("groups")
public class Group {

    public enum Type{
        STAFF, MANAGEMENT
    }

    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime created_at;
    @DBRef
    List<Achievement> achievements;
    @DBRef
    List<Award> awards;
    @DBRef
    List<Bazaar> bazaars;

    //Constructors
    public Group(){}

    public Group(String id, String name, Type type, LocalDateTime created_at,
                 List<Achievement> achievements, List<Award> awards, List<Bazaar> bazaars) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.created_at = created_at;
        this.achievements = achievements;
        this.awards = awards;
        this.bazaars = bazaars;
    }

    //Getters & Setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreated_at() { return created_at; }

    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public List<Achievement> getAchievements() { return achievements; }

    public void setAchievements(List<Achievement> achievements) { this.achievements = achievements; }

    public List<Award> getAwards() { return awards; }

    public void setAwards(List<Award> Awards) { this.awards = awards; }

    public List<Bazaar> getBazaars() { return bazaars; }

    public void setBazaars(List<Bazaar> bazaars) { this.bazaars = bazaars; }
}
