package com.example.e_commerce.network.service;

import com.example.e_commerce.network.model.request.LoginRequest;
import com.example.e_commerce.network.model.response.LoginResponse;
import com.example.e_commerce.network.model.request.RegisterBody;
import com.example.e_commerce.network.model.response.RegisterResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("user/auth/signup/")
    Call<RegisterResponse> register(@Body RegisterBody registerBody);

    @POST("user/auth/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/auth/login_with_google_and_facebook/")
    Call<LoginResponse> loginWithGoogle(@Body JsonObject body);
}
