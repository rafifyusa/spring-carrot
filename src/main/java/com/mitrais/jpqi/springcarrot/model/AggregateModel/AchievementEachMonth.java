package com.mitrais.jpqi.springcarrot.model.AggregateModel;

import com.mitrais.jpqi.springcarrot.model.Achievement;

import java.util.Set;

public class AchievementEachMonth {
    private String year;
    private String month;
    private Set<Achievement> achievements;

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public Set<Achievement> getAchievements() { return achievements; }

    public void setAchievements(Set<Achievement> achievements) { this.achievements = achievements; }
}
