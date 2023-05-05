package com.example.e_commerce.screens.payment;

import static com.example.e_commerce.screens.home.HomeProductAdapter.CompanionObject.ITEM_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentPaymentTestBinding;
import com.example.e_commerce.databinding.FragmentProductDetailBinding;
import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.service.PaymentService;
import com.example.e_commerce.network.service.ProductService;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

@AndroidEntryPoint
public class PaymentTestFragment extends Fragment {
    @Inject
    PaymentService paymentService;

    private FragmentPaymentTestBinding binding;
    private ProductModel productModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaymentTestBinding.inflate(inflater, container, false);
        binding.btnPay.setOnClickListener(v -> {
            CreateOrderRequest object = new CreateOrderRequest(5000, "dangdung", "description", "data", null, "12");
            Call<ResponseAPI<CreateOrderResponse>> call = paymentService.createOrder(object);

            call.enqueue(new Callback<ResponseAPI<CreateOrderResponse>>() {
                @Override
                public void onResponse(Call<ResponseAPI<CreateOrderResponse>> call, Response<ResponseAPI<CreateOrderResponse>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        ZaloPaySDK.getInstance().payOrder(
                                requireActivity(),
                                response.body().getData().getZptranstoken(),
                                "zpdk://app",
                                new PayOrderListener() {
                                    @Override
                                    public void onPaymentSucceeded(String transactionId, String transToken, String appTransID) {
                                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onPaymentCanceled(String s, String s1) {
                                        Toast.makeText(requireActivity(), "onPaymentCanceled", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                        Toast.makeText(requireActivity(), "onPaymentCanceled", Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<CreateOrderResponse>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        return binding.getRoot();
    }

    private void showToast(String mes) {
        Toast.makeText(requireActivity(), "Added to cart", Toast.LENGTH_LONG).show();
    }

}