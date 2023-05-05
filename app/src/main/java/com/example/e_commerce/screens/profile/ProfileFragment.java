package com.example.e_commerce.screens.profile;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentProfileBinding;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.profile.CurrentUserResponse;
import com.example.e_commerce.network.service.ProfileService;
import com.example.e_commerce.screens.auth.LoginActivity;
import com.example.e_commerce.utils.UserManager;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    @Inject
    ProfileService profileService;
    @Inject
    UserManager userManager;
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getView()).navigate(R.id.action_profileFragment_to_profileEditFragment);
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
                    CurrentUserResponse data = response.body().getData();
                    binding.emailInfo.setText(data.getEmail());
                    binding.phoneNumberInfo.setText(data.getTelephoneNumber());
                    binding.addressInfo.setText(data.getDeliveryAddress());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<CurrentUserResponse>> call, Throwable t) {

            }
        });

        return binding.getRoot();
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
                }
                else {
                    Toast.makeText(requireContext(), "An error has occurred!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<String>> call, Throwable t) {

            }
        });

    }

}