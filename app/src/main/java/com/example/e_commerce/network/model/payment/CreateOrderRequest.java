package com.example.e_commerce.network.model.payment;

import java.util.List;

public class CreateOrderRequest {
    private String appuser;
    private List<Item> item;
    private String description;
    private String embeddata;

    public CreateOrderRequest(String appuser, List<Item> item, String description, String embeddata) {
        this.appuser = appuser;
        this.item = item;
        this.description = description;
        this.embeddata = embeddata;
    }

    public String getAppuser() {
        return appuser;
    }

    public void setAppuser(String value) {
        this.appuser = value;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> value) {
        this.item = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getEmbeddata() {
        return embeddata;
    }

    public void setEmbeddata(String value) {
        this.embeddata = value;
    }
}

