package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Basket;

import java.util.List;

public class BasketResponse extends Response {
    Basket basket;
    List<Basket> listBasket;

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Basket> getListBasket() {
        return listBasket;
    }

    public void setListBasket(List<Basket> listBasket) {
        this.listBasket = listBasket;
    }
}
