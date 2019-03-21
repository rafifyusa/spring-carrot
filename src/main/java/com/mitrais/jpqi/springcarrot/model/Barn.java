package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Document("barns")
public class Barn {
    @Id
    private String Id;
    private String name;
    private Employee owner;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private Long totalCarrot;
    private Long carrotLeft;
    private boolean status;
    private boolean released;
    @DBRef
    private List<Award> awards;

    public Barn(){}
    public Barn(String id, String name, Employee owner, LocalDate startPeriod, LocalDate endPeriod,
                Long totalCarrot, Long carrotLeft, boolean status, boolean released, List<Award> awards) {
        Id = id;
        this.name = name;
        this.owner = owner;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.totalCarrot = totalCarrot;
        this.carrotLeft = carrotLeft;
        this.status = status;
        this.released = released;
        this.awards = awards;
    }

    //Getter & Setters

    public String getId() { return Id; }

    public void setId(String id) { Id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Employee getOwner() { return owner; }

    public void setOwner(Employee owner) { this.owner = owner; }

    public LocalDate getStartPeriod() { return startPeriod; }

    public void setStartPeriod(LocalDate startPeriod) { this.startPeriod = startPeriod; }

    public LocalDate getEndPeriod() { return endPeriod; }

    public void setEndPeriod(LocalDate endPeriod) { this.endPeriod = endPeriod; }

    public Long getTotalCarrot() { return totalCarrot; }

    public void setTotalCarrot(Long totalCarrot) { this.totalCarrot = totalCarrot; }

    public Long getCarrotLeft() { return carrotLeft; }

    public void setCarrotLeft(Long carrotLeft) { this.carrotLeft = carrotLeft; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }

    public boolean isReleased() { return released; }

    public void setReleased(boolean released) { this.released = released; }

    public List<Award> getAwards() { return awards; }

    public void setAwards(List<Award> awards) { this.awards = awards; }
}
