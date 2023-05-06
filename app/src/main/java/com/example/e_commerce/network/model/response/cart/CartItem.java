// CartItem.java

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType

package com.example.e_commerce.network.model.response.cart;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.e_commerce.model.ProductModel;

import java.io.Serializable;

public class CartItem implements Serializable, Parcelable {
    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
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

    protected CartItem(Parcel in) {
        id = in.readInt();
        user_id = in.readString();
        quantity = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(user_id);
        dest.writeInt(quantity);
    }
}
