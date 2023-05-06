package com.example.e_commerce.screens.payment;

import java.util.List;
import java.util.Map;

public class CreateOrderRequest {
    private String appuser;
    private String description;
    private String embeddata;

    public CreateOrderRequest(String appuser, String description, String embeddata) {
        this.appuser = appuser;
        this.description = description;
        this.embeddata = embeddata;
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
}