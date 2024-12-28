package com.example.mahara1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mahara1.restaurantOwner.data.Booking;

import java.util.List;

public class BookingAdapter  extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> bookinList;
    private Context context;

    public BookingAdapter(Context context, List<Booking> sessionList) {
        this.context = context;
        this.bookinList = sessionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking restaurant = bookinList.get(position);
        holder.customerName.setText(restaurant.getcustomerName());
        holder.customerEmail.setText(restaurant.getcustomerEmail());
        holder.customerPhone.setText(restaurant.getcustomerPhone());
        holder.sessionId.setText(restaurant.getrestaurantId());

    }

    @Override
    public int getItemCount() {
        return bookinList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName,customerEmail,customerPhone, sessionId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.customerName);
            customerEmail = itemView.findViewById(R.id.customerEmail);
            customerPhone = itemView.findViewById(R.id.customerPhone);
            sessionId =itemView.findViewById(R.id.restaurantId);

        }
    }
}