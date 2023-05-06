package com.example.e_commerce.screens.cart;

import static androidx.navigation.Navigation.findNavController;
import static com.example.e_commerce.screens.home.HomeProductAdapter.CompanionObject.ITEM_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentCartBinding;
import com.example.e_commerce.model.ProductModel;
import com.example.e_commerce.network.model.request.cart.AddToCartRequest;
import com.example.e_commerce.network.model.response.ResponseAPI;
import com.example.e_commerce.network.model.response.cart.CartItem;
import com.example.e_commerce.network.service.CartService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CartFragment extends Fragment implements CartItemListener {
    List<CartItem> cartItemsList;
    ArrayList<CartItem> selectedItems;
    CartProductAdapter cartProductAdapter;
    Float totalPrice;
    int totalSelected;
    @Inject
    CartService cartService;
    private FragmentCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        setUpCartItems();

        totalPrice = 0f;
        totalSelected = 0;
        selectedItems = new ArrayList<>();
        binding.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList(CART_ITEMS, selectedItems);
//                bundle.putFloat(TOTAL_PRICE, totalPrice);
//                if (!selectedItems.isEmpty()) {
//                    System.out.println("list items:"+selectedItems.size());
//                    findNavController(getView()).navigate(R.id.action_cartFragment_to_orderFragment, bundle);
//                }
                findNavController(getView()).navigate(R.id.action_cartFragment_to_paymentTestFragment);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedItems.clear();
    }

    private void setUpCartItems() {
        Call<ResponseAPI<List<CartItem>>> call = cartService.getCartItems();
        call.enqueue(new Callback<ResponseAPI<List<CartItem>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<CartItem>>> call, Response<ResponseAPI<List<CartItem>>> response) {
                if (response.isSuccessful()) {
                    cartItemsList = response.body().getData();
                    cartProductAdapter = new CartProductAdapter(cartItemsList, requireContext());
                    cartProductAdapter.setCartItemListener(CartFragment.this);
                    binding.cartRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.cartRcv.setAdapter(cartProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<CartItem>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void update() {
        cartProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void increaseQuantity(int position) {
        CartItem item = cartItemsList.get(position);
        int currentQuantity = item.getQuantity();
        Call<ResponseAPI<List<CartItem>>> call = cartService.updateCartItem(
                new AddToCartRequest(
                        "Update",
                        item.getId(),
                        (int) (currentQuantity + 1)
                ));
        call.enqueue(new Callback<ResponseAPI<List<CartItem>>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseAPI<List<CartItem>>> call, Response<ResponseAPI<List<CartItem>>> response) {
                if (response.isSuccessful()) {
                    cartItemsList.clear();
                    cartItemsList.addAll(response.body().getData());
                    update();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<CartItem>>> call, Throwable t) {

            }
        });

    }

    @Override
    public void decreaseQuantity(int position) {
        CartItem item = cartItemsList.get(position);
        long currentQuantity = item.getQuantity();
        if (currentQuantity > 1) {
            Call<ResponseAPI<List<CartItem>>> call = cartService.updateCartItem(
                    new AddToCartRequest(
                            "Update",
                            item.getId(),
                            (int) (currentQuantity - 1)
                    ));
            call.enqueue(new Callback<ResponseAPI<List<CartItem>>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<ResponseAPI<List<CartItem>>> call, Response<ResponseAPI<List<CartItem>>> response) {
                    if (response.isSuccessful()) {
                        cartItemsList.clear();
                        cartItemsList.addAll(response.body().getData());
                        update();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI<List<CartItem>>> call, Throwable t) {

                }
            });

        }

    }

    @Override
    public void deleteItem(int position) {
        CartItem item = cartItemsList.get(position);
        int currentQuantity = item.getQuantity();
        Call<ResponseAPI<List<CartItem>>> call = cartService.updateCartItem(
                new AddToCartRequest(
                        "Remove",
                        item.getId(),
                        0
                ));
        call.enqueue(new Callback<ResponseAPI<List<CartItem>>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseAPI<List<CartItem>>> call, Response<ResponseAPI<List<CartItem>>> response) {
                if (response.isSuccessful()) {
                    cartItemsList.clear();
                    cartItemsList.addAll(response.body().getData());
                    update();
                    totalPrice = 0f;
                    totalSelected = 0;
                    binding.tvTotalItem.setText("Total: " + totalSelected + " item");
                    binding.totalPriceTxt.setText(totalPrice.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<CartItem>>> call, Throwable t) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSelectCartItem(float price, int position, boolean isChecked) {

        CartItem item = cartItemsList.get(position);
        if (isChecked) {
            totalSelected += 1;
            selectedItems.add(item);
        } else {
            totalSelected -= 1;
            selectedItems.remove(cartItemsList.get(position));
        }
        totalPrice += price;
        binding.tvTotalItem.setText("Total: " + totalSelected + " item");
        binding.totalPriceTxt.setText(totalPrice.toString());
    }

    @Override
    public void chooseCartItem(int position) {

    }

    public static class CompanionObject {
        public static final String CART_ITEMS = "CartItem";
        public static final String TOTAL_PRICE = "TotalPrice";
    }
}