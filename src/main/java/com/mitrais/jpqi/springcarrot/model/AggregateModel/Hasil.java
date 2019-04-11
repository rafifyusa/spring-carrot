package com.mitrais.jpqi.springcarrot.model.AggregateModel;

import com.mitrais.jpqi.springcarrot.model.Basket;

public class Hasil {
    Basket detail;
    String id;
    Long total;
    int donation;
    int shared;
    int reward;
    int carrotThisMonth;

    public int getCarrotThisMonth() {
        return carrotThisMonth;
    }

    public void setCarrotThisMonth(int carrotThisMonth) {
        this.carrotThisMonth = carrotThisMonth;
    }

    public int getDonation() {
        return donation;
    }

    public void setDonation(int donation) {
        this.donation = donation;
    }

    public int getShared() {
        return shared;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public Basket getDetail() {
        return detail;
    }

    public void setDetail(Basket detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
