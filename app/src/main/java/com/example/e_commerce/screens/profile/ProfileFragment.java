package com.example.e_commerce.screens.profile;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentProfileBinding;
import com.example.e_commerce.network.model.payment.response.OrderDetailResponse;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.profile.CurrentUserResponse;
import com.example.e_commerce.network.service.PaymentService;
import com.example.e_commerce.network.service.ProfileService;
import com.example.e_commerce.screens.auth.LoginActivity;
import com.example.e_commerce.utils.UserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    static String USER = "user";
    @Inject
    ProfileService profileService;
    @Inject
    UserManager userManager;
    @Inject
    PaymentService paymentService;
    CurrentUserResponse userResponse;
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(USER, userResponse);
                findNavController(getView()).navigate(R.id.action_profileFragment_to_profileEditFragment, bundle);
            }
        });
        binding.btnLogout.setOnClickListener(view -> {
            logout();
        });
        Call<ResponseAPI<CurrentUserResponse>> call = profileService.getUserInfo();

        call.enqueue(new Callback<ResponseAPI<CurrentUserResponse>>() {
            @Override
            public void onResponse(Call<ResponseAPI<CurrentUserResponse>> call, Response<ResponseAPI<CurrentUserResponse>> response) {
                if (response.isSuccessful()) {
                    userResponse = response.body().getData();
                    binding.nameInfo.setText(userResponse.getName());
                    binding.emailInfo.setText(userResponse.getEmail());
                    binding.phoneNumberInfo.setText(userResponse.getTelephoneNumber());
                    binding.addressInfo.setText(userResponse.getDeliveryAddress());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<CurrentUserResponse>> call, Throwable t) {

            }
        });

        setUpOrderStatus();

        return binding.getRoot();
    }

    private void setUpOrderStatus() {
        Call<ResponseAPI<List<OrderDetailResponse>>> call = paymentService.createOrder();

        call.enqueue(new Callback<ResponseAPI<List<OrderDetailResponse>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<OrderDetailResponse>>> call, Response<ResponseAPI<List<OrderDetailResponse>>> response) {
                if (response.isSuccessful()) {
                    List<OrderDetailResponse> userOrderList = response.body().getData();
                    UserOrderAdapter userOrderAdapter = new UserOrderAdapter(userOrderList, requireContext());
                    binding.orderRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.orderRcv.setAdapter(userOrderAdapter);
                } else {
                    System.out.println("fail to fetch order");
                }

            }

            @Override
            public void onFailure(Call<ResponseAPI<List<OrderDetailResponse>>> call, Throwable t) {

            }
        });
    }

    private void logout() {
        JsonObject data = new JsonObject();
        data.addProperty("refresh", userManager.getRefreshToken());
        Call<ResponseAPI<String>> call = profileService.logout(data);

        call.enqueue(new Callback<ResponseAPI<String>>() {
            @Override
            public void onResponse(Call<ResponseAPI<String>> call, Response<ResponseAPI<String>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                    userManager.deleteToken();
                    FirebaseAuth.getInstance().signOut();
                } else {
                    Toast.makeText(requireContext(), "An error has occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<String>> call, Throwable t) {

            }
        });

    }

}