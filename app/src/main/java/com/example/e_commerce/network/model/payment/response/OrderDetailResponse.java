// OrderDetailResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.e_commerce.network.model.payment.response;

import java.util.List;

public class OrderDetailResponse {
    private String date;
    private long id;
    private String status;
    private List<OrderItem> order_items;

    public String getDate() {
        return date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public List<OrderItem> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderItem> value) {
        this.order_items = value;
    }
}

