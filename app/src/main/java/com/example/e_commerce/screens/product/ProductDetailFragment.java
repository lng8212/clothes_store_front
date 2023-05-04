package com.example.e_commerce.screens.product;

import static com.example.e_commerce.screens.home.HomeProductAdapter.CompanionObject.ITEM_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.e_commerce.databinding.FragmentProductDetailBinding;
import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.service.ProductService;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {
    @Inject
    ProductService productService;

    private FragmentProductDetailBinding binding;
    private ProductModel productModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            Call<ResponseAPI<ProductModel>> call = productService.getProductDetail(getArguments().getInt(ITEM_KEY));
            call.enqueue(new Callback<ResponseAPI<ProductModel>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<ResponseAPI<ProductModel>> call, Response<ResponseAPI<ProductModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            productModel = response.body().getData();
                            Glide.with(requireContext())
                                    .load(productModel.getImage())
                                    .into(binding.productImg);
                            binding.productNameTxt.setText(productModel.getName());
                            binding.productDescTxt.setText(productModel.getDescription());
                            binding.productPriceTxt.setText("Price: " + productModel.getPrice());
//                            binding.statusTxt.setText(productDetail.getProductStatus());
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<ProductModel>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        binding.cartBtn.setOnClickListener(v -> {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("quantity", 1L);
            requestBody.addProperty("id", productModel.getId());
            Call<ResponseAPI<ProductModel>> call = productService.addToCart(requestBody);
            call.enqueue(new Callback<ResponseAPI<ProductModel>>() {
                @Override
                public void onResponse(Call<ResponseAPI<ProductModel>> call, Response<ResponseAPI<ProductModel>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireActivity(), "Added to cart", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<ProductModel>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        return binding.getRoot();
    }
}