package com.mitrais.jpqi.springcarrot.model.AggregateModel;

import javax.persistence.Id;

public class GroupCount {
    @Id
    private int id;
    private String group;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
