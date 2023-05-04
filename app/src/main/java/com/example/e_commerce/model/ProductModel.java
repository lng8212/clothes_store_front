package com.example.e_commerce.model;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;
import java.util.ArrayList;

public class ProductModel implements Serializable {
    private int id;
    private String name;
    private String supplier;
    private Float price;
    private Float old_price;
    private Float discount;
    private String image;
    private String description;
    private ArrayList<Category> categories;

    public ProductModel(int id, String name, String supplier, Float price, Float old_price, Float discount, String image, String description, ArrayList<Category> categories) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.price = price;
        this.old_price = old_price;
        this.discount = discount;
        this.image = image;
        this.description = description;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getOld_price() {
        return old_price;
    }

    public void setOld_price(Float old_price) {
        this.old_price = old_price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
