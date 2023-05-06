package com.example.e_commerce.network.model.payment;

public class Item {
    private long itemid;
    private String itemname;
    private float itemprice;
    private long itemquantity;

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long value) {
        this.itemid = value;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String value) {
        this.itemname = value;
    }

    public float getItemprice() {
        return itemprice;
    }

    public void setItemprice(long value) {
        this.itemprice = value;
    }

    public long getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(long value) {
        this.itemquantity = value;
    }

    public Item(long itemid, String itemname, float itemprice, long itemquantity) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemquantity = itemquantity;
    }
}

