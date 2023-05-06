package com.example.e_commerce.screens.order;

import static com.example.e_commerce.screens.cart.CartFragment.CompanionObject.CART_ITEMS;
import static com.example.e_commerce.screens.cart.CartFragment.CompanionObject.TOTAL_PRICE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_commerce.databinding.FragmentOrderBinding;
import com.example.e_commerce.network.model.payment.CreateOrderRequest;
import com.example.e_commerce.network.model.payment.Item;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.cart.CartItem;
import com.example.e_commerce.network.model.response.profile.CurrentUserResponse;
import com.example.e_commerce.network.service.OrderService;
import com.example.e_commerce.network.service.PaymentService;
import com.example.e_commerce.network.service.ProfileService;
import com.example.e_commerce.screens.payment.CreateOrderDataResponse;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

@AndroidEntryPoint
public class OrderFragment extends Fragment {
    FragmentOrderBinding binding;
    OrderItemAdapter orderItemAdapter;
    List<CartItem> cartItems;
    CurrentUserResponse userInfo;
    @Inject
    ProfileService profileService;
    @Inject
    OrderService orderService;
    @Inject
    PaymentService paymentService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            cartItems = getArguments().getParcelableArrayList(CART_ITEMS);
            binding.costValue.setText(String.valueOf(getArguments().getFloat(TOTAL_PRICE)));
            setUpOrderItems();
            setUpUserInfo();

            binding.confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createOrder();
                }
            });
        }
        return binding.getRoot();
    }

    private void createOrder() {

        List<Item> itemList = cartItems.stream()
                .map(source -> new Item(source.getId(), source.getProduct().getName(),source.getProduct().getPrice(),source.getQuantity()))
                .collect(Collectors.toList());
        CreateOrderRequest object = new CreateOrderRequest("dangdung", itemList,"description", "data");
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
                    } else {
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
    }

    private void setUpUserInfo() {
        Call<ResponseAPI<CurrentUserResponse>> call = profileService.getUserInfo();

        call.enqueue(new Callback<ResponseAPI<CurrentUserResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseAPI<CurrentUserResponse>> call, Response<ResponseAPI<CurrentUserResponse>> response) {
                if (response.isSuccessful()) {
                    CurrentUserResponse currentUserResponse = response.body().getData();
                    userInfo = currentUserResponse;
                    binding.nameValue.setText(currentUserResponse.getName());
                    binding.phoneValue.setText(currentUserResponse.getTelephoneNumber());
                    binding.addressValue.setText(currentUserResponse.getDeliveryAddress());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<CurrentUserResponse>> call, Throwable t) {

            }
        });
    }

    private void setUpOrderItems() {
        orderItemAdapter = new OrderItemAdapter(cartItems, requireContext());
        binding.orderItemsRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.orderItemsRcv.setAdapter(orderItemAdapter);
    }
}