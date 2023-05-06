package com.example.e_commerce.screens.payment;

import static com.example.e_commerce.screens.home.HomeProductAdapter.CompanionObject.ITEM_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
            CreateOrderRequest object = new CreateOrderRequest("dangdung", "description", "data");
            Call<ResponseAPI<CreateOrderDataResponse>> call = paymentService.createOrder(object);

            call.enqueue(new Callback<ResponseAPI<CreateOrderDataResponse>>() {
                @Override
                public void onResponse(Call<ResponseAPI<CreateOrderDataResponse>> call, Response<ResponseAPI<CreateOrderDataResponse>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String transToken = response.body().getData().getCreateOrderResponse().getZptranstoken();
                        Toast.makeText(requireContext(), "Order id: " + response.body().getData().getOrderId(), Toast.LENGTH_LONG).show();
                        if (transToken.isEmpty()) {
                            Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                        }
                        else {
                            ZaloPaySDK.getInstance().payOrder(
                                    requireActivity(),
                                    transToken,
                                    "", //zpdk://app
                                    new PayOrderListener() {
                                        @Override
                                        public void onPaymentSucceeded(String transactionId, String transToken, String appTransID) {
                                            Log.e("TAG", "onPaymentSucceeded: ");
                                        }

                                        @Override
                                        public void onPaymentCanceled(String s, String s1) {
                                            Log.e("TAG", "onPaymentCanceled: ");
                                        }

                                        @Override
                                        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                            Log.e("TAG", "onPaymentError: ");
                                        }
                                    });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<CreateOrderDataResponse>> call, Throwable t) {
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