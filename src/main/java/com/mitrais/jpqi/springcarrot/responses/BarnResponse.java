package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Barn;

import java.util.List;

public class BarnResponse extends Response {
    Barn barn;
    List<Barn> listBarn;

    public Barn getBarn() {
        return barn;
    }

    public void setBarn(Barn barn) {
        this.barn = barn;
    }

    public List<Barn> getListBarn() {
        return listBarn;
    }

    public void setListBarn(List<Barn> listBarn) {
        this.listBarn = listBarn;
    }
}
