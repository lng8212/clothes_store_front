// CartItem.java

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType

package com.example.e_commerce.network.model.response.cart;

import com.example.e_commerce.model.ProductModel;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int id;
    private String user_id;
    private int quantity;
    private ProductModel product;

    public CartItem(int id, String user_id, int quantity, ProductModel product) {
        this.id = id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String cart_id) {
        this.user_id = cart_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
