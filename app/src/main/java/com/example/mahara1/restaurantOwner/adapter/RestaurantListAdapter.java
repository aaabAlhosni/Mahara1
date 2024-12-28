package com.example.mahara1.restaurantOwner.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mahara1.R;
import com.example.mahara1.UpdateSessionRegistrationActivity;
import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.restaurantOwner.data.Restaurant;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<Restaurant> sessionList1;
    private Context context;

    public RestaurantListAdapter(Context context, List<Restaurant> sessionList1) {
        this.context = context;
        this.sessionList1 = sessionList1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_delete_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = sessionList1.get(position);
        holder.restaurantNameTextView.setText(restaurant.getOwnerName());
        holder.restaurantAddressTextView.setText(restaurant.getRestaurantAddress());
        holder.restaurantEmailTextView.setText(restaurant.getRestaurantEmail());
        holder.restaurantAddressTextView.setText(restaurant.getRestaurantPhone());
        holder.restaurantPhoneTextView.setText(restaurant.getRestaurantDescription());
        // Add more bindings as per your Restaurant object

        // Load logo image using Glide

           try {
               String logoUrl = sessionList1.get(position).getLogoUrl();
               Glide.with(context)
                       .load(Uri.parse(logoUrl))
                       .into(holder.logoImageView);
           }catch (Exception e){}
            holder.textDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper dbHelper = new DatabaseHelper(context.getApplicationContext());

                    dbHelper.deleteOwnerByCorporation(sessionList1.get(position).getCorporationNum());
                        // Remove the item from your data set
                        sessionList1.remove(position);
                        // Notify the adapter that an item has been removed
                        notifyItemRemoved(position);
                        // This line below is optional, it notifies that any item that was in a position after the
                        // item removed may now be in a previous position (it updates their position)
                        notifyItemRangeChanged(position, sessionList1.size());
                }
            });
        holder.textEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, UpdateSessionRegistrationActivity.class);
                intent.putExtra("update","true");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sessionList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameTextView,restaurantDescriptionTextView,restaurantEmailTextView,restaurantPhoneTextView;
        TextView restaurantAddressTextView,textDelete,textEdit;
        ImageView logoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.textViewName);
            restaurantAddressTextView = itemView.findViewById(R.id.textViewAddress);
            textDelete = itemView.findViewById(R.id.textDelete);
            logoImageView = itemView.findViewById(R.id.imageViewLogo);
            restaurantDescriptionTextView=itemView.findViewById(R.id.textViewDescription);
            restaurantEmailTextView=itemView.findViewById(R.id.textViewEmail);
            restaurantPhoneTextView=itemView.findViewById(R.id.textViewPhoneNumber);
            textEdit =itemView.findViewById(R.id.textEdit);
            // Initialize other views as needed
        }
    }
}
