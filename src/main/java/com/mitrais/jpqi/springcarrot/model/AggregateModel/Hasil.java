package com.mitrais.jpqi.springcarrot.model.AggregateModel;

import com.mitrais.jpqi.springcarrot.model.Basket;

public class Hasil {
    Basket detail;
    String id;
    Long total;

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
