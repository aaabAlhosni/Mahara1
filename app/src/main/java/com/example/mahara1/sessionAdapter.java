package com.example.mahara1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.data.Restaurant;

import java.util.List;

public class sessionAdapter extends ArrayAdapter<Restaurant> {
    List<Restaurant> sessionModelList;
    Context context;
    int res;

    public sessionAdapter(@NonNull Context context, int resource, List<Restaurant> list) {
        super(context, resource,list);
        this.res=resource;
        this.sessionModelList =list;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;

        if(v==null){
            LayoutInflater l;
            l=LayoutInflater.from(context);
            v=l.inflate(res,null);
        }
        Log.e("item name ", sessionModelList.get(position).getRestaurantName());

        TextView textView=v.findViewById(R.id.txtview);
        TextView txtAddress=v.findViewById(R.id.txtAddress);
        TextView textEmail=v.findViewById(R.id.textViewEmail);
        TextView txtPhone=v.findViewById(R.id.txtPhoneNumber);
        TextView txtAdditionalDetail = v.findViewById(R.id.txtAdditionalDetail);
        ImageView resturantLogo = v.findViewById(R.id.resturantLogo);
        Button btnBookNow=v.findViewById(R.id.btnBookNow);
        txtAdditionalDetail.setText(sessionModelList.get(position).getAdditionalDetails());
        textEmail.setText(sessionModelList.get(position).getRestaurantEmail());
        txtPhone.setText(sessionModelList.get(position).getRestaurantPhone());
        textView.setText(sessionModelList.get(position).getRestaurantName());
        txtAddress.setText(sessionModelList.get(position).getRestaurantAddress());
          try{
              String logoUrl = sessionModelList.get(position).getLogoUrl();
              Glide.with(context)
                    .load(Uri.parse(logoUrl))
                    .into(resturantLogo);
          }catch (Exception e){

          }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"successfully booked Session",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent i=new Intent(context,MapsActivity.class);
                SessionTypeWiseActivity.name = sessionModelList.get(position).getRestaurantName();
                SessionTypeWiseActivity.address = sessionModelList.get(position).getRestaurantAddress();
                context.startActivity(i);

            }
        });

            btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"successfully booked Session",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                DatabaseHelper dbHelper = new DatabaseHelper(getContext());

                dbHelper.insertCustomerBookingTable(SharedPrefManager.getInstance(getContext()).getName(),SharedPrefManager.getInstance(getContext()).getEmail(),SharedPrefManager.getInstance(getContext()).getPhone(),
                        sessionModelList.get(position).getCorporationNum(), sessionModelList.get(position).getRestaurantEmail(), sessionModelList.get(position).getRestaurantPhone() );
            }
        });
        return v;
    }

}
