package com.example.e_commerce.network.service;
// create profile request + response and add them here later

import com.example.e_commerce.network.model.request.UpdateUserRequest;
import com.example.e_commerce.network.model.response.profile.CurrentUserResponse;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.profile.UpdateUserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ProfileService {
    @GET("user/profile/")
    Call<ResponseAPI<CurrentUserResponse>> getUserInfo();

    @PUT("user/update/")
    Call<ResponseAPI<String>> updateUserInfo(@Body UpdateUserRequest updateUserRequest);

    @POST("user/auth/logout/")
    Call<ResponseAPI<String>> logout(@Body JsonObject body);
}
