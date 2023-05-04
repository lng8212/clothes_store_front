package com.example.e_commerce.network.service;

import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.request.cart.AddToCartRequest;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {
    @POST("catalog/products/")
    Call<ResponseAPI<List<ProductModel>>> searchProducts(@Body JsonObject object);

    @GET("catalog/product/detail/{id}")
    Call<ResponseAPI<ProductModel>> getProductDetail(@Path("id") int id);

    @POST("catalog/products/")
    Call<ResponseAPI<List<ProductModel>>> getAllProduct();

    @POST("catalog/product/add_to_cart/")
    Call<ResponseAPI<ProductModel>> addToCart(@Body JsonObject body);
}
