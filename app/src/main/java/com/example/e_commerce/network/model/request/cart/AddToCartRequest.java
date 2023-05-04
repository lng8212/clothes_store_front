package com.example.e_commerce.network.model.request.cart;

public class AddToCartRequest {
    private String submit;
    private int item_id;
    private int quantity;

    public AddToCartRequest(String submit, int item_id, int quantity) {
        this.submit = submit;
        this.item_id = item_id;
        this.quantity = quantity;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
