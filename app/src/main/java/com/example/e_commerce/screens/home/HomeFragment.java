package com.example.e_commerce.screens.home;

import static androidx.navigation.Navigation.findNavController;
import static com.example.e_commerce.screens.home.HomeProductAdapter.CompanionObject.ITEM_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentHomeBinding;
import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.request.SearchProductRequest;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.service.ProductService;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements OnClickProductItem {
    @Inject
    ProductService productService;
    private HomeProductAdapter productAdapter;
    private FragmentHomeBinding binding;
    private SearchProductRequest currentSearchProductRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        currentSearchProductRequest = new SearchProductRequest(null, "asc", 1, null);

        productAdapter = new HomeProductAdapter(new ArrayList<>(), requireContext());
        productAdapter.onClickProductItem = this;
        binding.newProductRcv.setAdapter(productAdapter);

        setUpListProduct();
        setUpOnClick();
        return binding.getRoot();
    }

    private void setUpOnClick() {
        binding.searchBtn.setOnClickListener(view -> {
            String query = binding.searchEdt.getText().toString();
            if (!query.isEmpty()) {
                searchProducts(query);
            }
        });
    }

    private void setUpListProduct() {
        Call<ResponseAPI<List<ProductModel>>> call = productService.getAllProduct();
        call.enqueue(new Callback<ResponseAPI<List<ProductModel>>>() {

            @Override
            public void onResponse(Call<ResponseAPI<List<ProductModel>>> call, Response<ResponseAPI<List<ProductModel>>> response) {
                if (response.body() != null) {
                    List<ProductModel> types = response.body().getData();
                    ArrayList<ProductModel> typeList = new ArrayList<>(types);
                    productAdapter.setListProduct(typeList);
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<ProductModel>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.searchEdt.setText("");
    }

    @Override
    public void clickProduct(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_KEY, productModel.getId());
        findNavController(getView()).navigate(R.id.action_homeFragment_to_productDetailFragment, bundle);
    }

    void searchProducts(String query) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("key_word", query);
        Call<ResponseAPI<List<ProductModel>>> call = productService.searchProducts(requestBody);
        call.enqueue(new Callback<ResponseAPI<List<ProductModel>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<ProductModel>>> call, Response<ResponseAPI<List<ProductModel>>> response) {
                if (response.body() != null) {
                    List<ProductModel> types = response.body().getData();
                    ArrayList<ProductModel> typeList = new ArrayList<>(types);
                    productAdapter.setListProduct(typeList);
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<ProductModel>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}