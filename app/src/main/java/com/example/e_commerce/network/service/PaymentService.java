package com.example.e_commerce.network.service;

import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.screens.payment.CreateOrderDataResponse;
import com.example.e_commerce.screens.payment.CreateOrderRequest;
import com.example.e_commerce.screens.payment.CreateOrderResponse;
import com.example.e_commerce.screens.payment.CreateOrderResponseBody;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentService {
    @POST("payment/create_order/")
    Call<ResponseAPI<CreateOrderDataResponse>> createOrder(@Body CreateOrderRequest object);

}
