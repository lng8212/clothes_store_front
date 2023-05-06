package com.example.e_commerce.screens.profile;

import static androidx.navigation.Navigation.findNavController;
import static com.example.e_commerce.screens.profile.ProfileFragment.USER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentProfileEditBinding;
import com.example.e_commerce.network.model.request.UpdateUserRequest;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.profile.CurrentUserResponse;
import com.example.e_commerce.network.model.response.profile.UpdateUserResponse;
import com.example.e_commerce.network.service.ProfileService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProfileEditFragment extends Fragment {

    @Inject
    ProfileService profileService;
    private FragmentProfileEditBinding binding;
    private UpdateUserRequest updateUserRequest;
    private CurrentUserResponse userResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileEditBinding.inflate(inflater, container, false);
        // initiate updateUserRequest
        if (getArguments() != null) {
            userResponse = getArguments().getParcelable(USER);

            binding.editEmail.setText(userResponse.getEmail());
            binding.editPhone.setText(userResponse.getTelephoneNumber());
            binding.editAddress.setText(userResponse.getDeliveryAddress());
            binding.editName.setText(userResponse.getName());
        }
        binding.updateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.editPhone.getText().toString();
                String address = binding.editAddress.getText().toString();
                String mail = binding.editEmail.getText().toString();
                String name = binding.editName.getText().toString();
                updateUserRequest = new UpdateUserRequest(name,mail,phone, address);
                Call<ResponseAPI<String>> call = profileService.updateUserInfo(updateUserRequest);

                call.enqueue(new Callback<ResponseAPI<String>>() {
                    @Override
                    public void onResponse(Call<ResponseAPI<String>> call, Response<ResponseAPI<String>> response) {
                        if (response.isSuccessful()) {
                            findNavController(getView()).navigate(R.id.action_profileEditFragment_to_profileFragment);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI<String>> call, Throwable t) {

                    }
                });
            }
        });


        return binding.getRoot();
    }
}