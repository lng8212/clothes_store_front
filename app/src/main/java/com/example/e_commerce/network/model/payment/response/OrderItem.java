package com.example.e_commerce.network.model.payment.response;

public class OrderItem {
    private String product;
    private long quantity;
    private float price;
    private long id;

    public String getProduct() {
        return product;
    }

    public void setProduct(String value) {
        this.product = value;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long value) {
        this.quantity = value;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float value) {
        this.price = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }
}
