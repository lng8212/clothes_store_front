package com.example.e_commerce.screens.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.OrderStatusBinding;
import com.example.e_commerce.network.model.payment.response.OrderDetailResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ItemViewHolder> {
    private List<OrderDetailResponse> userOrderList;
    private Context context;

    public UserOrderAdapter(List<OrderDetailResponse> userOrderList, Context context) {
        this.userOrderList = userOrderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status, parent, false);
        OrderStatusBinding binding = OrderStatusBinding.bind(view);
        return new ItemViewHolder(view, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        OrderDetailResponse order = userOrderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return userOrderList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        OrderStatusBinding binding;

        public ItemViewHolder(View itemView, OrderStatusBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(OrderDetailResponse order) {
            binding.orderId.setText("Mã: " + order.getid());
            binding.orderStatus.setText(order.getStatus());

            ZonedDateTime dateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                dateTime = ZonedDateTime.parse(order.getDate());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedString = dateTime.format(formatter);
                binding.orderDateTxt.setText(formattedString);
            }

        }
    }
}
