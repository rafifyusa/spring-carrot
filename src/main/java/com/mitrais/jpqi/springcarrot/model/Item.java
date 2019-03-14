package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "items")
public class Item {

    @Id
    private int id;
    private String itemName;
    private String itemDescription;
    private String pictureUrl;
    private int exchangeRate;
    private int totalItem;
    private boolean approvalStatus;
    private boolean saleStatus;
    private int itemSold;
    @DBRef
    private Bazaar bazaar;

    public Item() {}

    public Item(int id, String itemName, String itemDescription, String pictureUrl, int exchangeRate,
                int totalItem, boolean approvalStatus, boolean saleStatus, int itemSold, Bazaar bazaar) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.pictureUrl = pictureUrl;
        this.exchangeRate = exchangeRate;
        this.totalItem = totalItem;
        this.approvalStatus = approvalStatus;
        this.saleStatus = saleStatus;
        this.itemSold = itemSold;
        this.bazaar = bazaar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(int exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Bazaar getBazaar() {
        return bazaar;
    }

    public void setBazaar(Bazaar bazaar) {
        this.bazaar = bazaar;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public boolean isSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(boolean saleStatus) {
        this.saleStatus = saleStatus;
    }

    public int getItemSold() {
        return itemSold;
    }

    public void setItemSold(int itemSold) {
        this.itemSold = itemSold;
    }

}