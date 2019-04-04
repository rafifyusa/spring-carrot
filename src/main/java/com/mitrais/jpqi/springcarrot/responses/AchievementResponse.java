package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import java.util.List;

public class AchievementResponse extends Response {
    Achievement achievement;
    List<Achievement> listAchievement;

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public List<Achievement> getListAchievement() {
        return listAchievement;
    }

    public void setListAchievement(List<Achievement> listAchievement) {
        this.listAchievement = listAchievement;
    }
}
