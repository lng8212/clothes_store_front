package com.example.e_commerce.network.service;

import com.example.e_commerce.network.model.payment.CreateOrderRequest;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.screens.payment.CreateOrderDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("payment/create_order/")
    Call<ResponseAPI<CreateOrderDataResponse>> createOrder(@Body CreateOrderRequest object);

}
