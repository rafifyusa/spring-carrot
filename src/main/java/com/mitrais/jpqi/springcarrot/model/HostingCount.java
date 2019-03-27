package com.mitrais.jpqi.springcarrot.model;

import org.bson.types.ObjectId;

public class HostingCount {
    private String name;
    private long total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
