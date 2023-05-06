package com.example.e_commerce.screens.payment;

public class CreateOrderDataResponse {
    private int orderId;
    private CreateOrderResponse createOrderResponse;

    public CreateOrderDataResponse(int orderId, CreateOrderResponse createOrderResponse) {
        this.orderId = orderId;
        this.createOrderResponse = createOrderResponse;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public CreateOrderResponse getCreateOrderResponse() {
        return createOrderResponse;
    }

    public void setCreateOrderResponse(CreateOrderResponse createOrderResponse) {
        this.createOrderResponse = createOrderResponse;
    }
}
