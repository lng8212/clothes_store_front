package com.example.e_commerce;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

@HiltAndroidApp
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZaloPaySDK.init(554, Environment.SANDBOX);
    }
}
