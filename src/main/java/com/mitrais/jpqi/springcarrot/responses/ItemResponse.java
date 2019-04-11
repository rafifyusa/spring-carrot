package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Item;
//import jdk.internal.dynalink.linker.LinkerServices;

import java.util.List;

public class ItemResponse extends Response{
    Item item;
    List<Item> listItem;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getListItem() {
        return listItem;
    }

    public void setListItem(List<Item> listItem) {
        this.listItem = listItem;
    }
}
