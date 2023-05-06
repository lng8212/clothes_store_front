package com.example.e_commerce.screens.payment;

public class CreateOrderResponseBody {
    private CreateOrderResponse response;


    public CreateOrderResponseBody(CreateOrderResponse response) {
        this.response = response;
    }

    public CreateOrderResponse getResponse() {
        return response;
    }

    public void setResponse(CreateOrderResponse response) {
        this.response = response;
    }
}
