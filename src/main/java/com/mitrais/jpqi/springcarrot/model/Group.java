package com.mitrais.jpqi.springcarrot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Employee owner;
    @DBRef
    List<Achievement> achievements;
    @DBRef
    List<Award> awards;
    @DBRef
    List<Bazaar> bazaars;
    @DBRef
    List<SocialFoundation> socialFoundations;

    //Constructors
    public Group(){}

    public Group(String id, String name, Type type, LocalDateTime created_at, Employee owner,
                 List<Achievement> achievements, List<Award> awards, List<Bazaar> bazaars,
                 List<SocialFoundation> socialFoundations) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.created_at = created_at;
        this.owner = owner;
        this.achievements = achievements;
        this.awards = awards;
        this.bazaars = bazaars;
        this.socialFoundations = socialFoundations;
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

    public void setAwards(List<Award> awards) { this.awards = awards; }

    public List<Bazaar> getBazaars() { return bazaars; }

    public void setBazaars(List<Bazaar> bazaars) { this.bazaars = bazaars; }

    public List<SocialFoundation> getSocialFoundations() { return socialFoundations; }

    public void setSocialFoundations(List<SocialFoundation> socialFoundations) { this.socialFoundations = socialFoundations; }

    public Employee getOwner() { return owner; }

    public void setOwner(Employee owner) { this.owner = owner; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return id != null ? id.equals(group.id) : group.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
