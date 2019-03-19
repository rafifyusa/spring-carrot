package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "achievementHistory")
public class AchievementHistory {
    @Id
    private String id;
    private LocalDateTime timeAchieved;
    @DBRef
    private Employee employee;
    @DBRef
    private Achievement achievement;

    public AchievementHistory() {}

    public AchievementHistory(String id, LocalDateTime timeAchieved, Employee employee, Achievement achievement) {
        this.id = id;
        this.timeAchieved = timeAchieved;
        this.employee = employee;
        this.achievement = achievement;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimeAchieved() {
        return timeAchieved;
    }

    public void setTimeAchieved(LocalDateTime timeAchieved) {
        this.timeAchieved = timeAchieved;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}