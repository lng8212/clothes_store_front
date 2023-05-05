package com.example.e_commerce.screens.payment;

import java.util.List;
import java.util.Map;

public class CreateOrderRequest {
    private int amount;
    private String appuser;
    private String description;
    private String embeddata;
    private List<Map<String, Object>> item;
    private String orderid;

    public CreateOrderRequest(int amount, String appUser, String description, String embedData, List<Map<String, Object>> item, String orderId) {
        this.amount = amount;
        this.appuser = appUser;
        this.description = description;
        this.embeddata = embedData;
        this.item = item;
        this.orderid = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAppuser() {
        return appuser;
    }

    public void setAppuser(String appuser) {
        this.appuser = appuser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmbeddata() {
        return embeddata;
    }

    public void setEmbeddata(String embeddata) {
        this.embeddata = embeddata;
    }

    public List<Map<String, Object>> getItem() {
        return item;
    }

    public void setItem(List<Map<String, Object>> item) {
        this.item = item;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}