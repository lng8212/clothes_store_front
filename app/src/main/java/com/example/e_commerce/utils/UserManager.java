package com.example.e_commerce.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class UserManager {
    static String ACCESS_TOKEN = "access_token";
    static String REFRESH_TOKEN = "refresh_token";
    private final SharedPreferences sharedPreferences;

    @Inject
    public UserManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REFRESH_TOKEN, refreshToken).apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN, "");
    }

    public void deleteToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, "").apply();
        editor.putString(REFRESH_TOKEN, "").apply();
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }
}
