package com.example.e_commerce.screens.payment;

public class CreateOrderResponse {
    private String zptranstoken;
    private String orderurl;
    private int returncode;
    private String returnmessage;

    public CreateOrderResponse(String zptranstoken, String orderurl, int returncode, String returnmessage) {
        this.zptranstoken = zptranstoken;
        this.orderurl = orderurl;
        this.returncode = returncode;
        this.returnmessage = returnmessage;
    }

    public String getZptranstoken() {
        return zptranstoken;
    }

    public void setZptranstoken(String zptranstoken) {
        this.zptranstoken = zptranstoken;
    }

    public String getOrderurl() {
        return orderurl;
    }

    public void setOrderurl(String orderurl) {
        this.orderurl = orderurl;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getReturnmessage() {
        return returnmessage;
    }

    public void setReturnmessage(String returnmessage) {
        this.returnmessage = returnmessage;
    }
}