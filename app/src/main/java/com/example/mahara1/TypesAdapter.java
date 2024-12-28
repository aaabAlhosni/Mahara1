package com.example.mahara1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class TypesAdapter extends ArrayAdapter<SessionModel> {
    List<SessionModel> sessionTypeModelList;
    Context context;
    int res;

    public TypesAdapter(@NonNull Context context, int resource, List<SessionModel> list) {
        super(context, resource, list);
        this.res = resource;
        this.sessionTypeModelList = list;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater l;
            l = LayoutInflater.from(context);
            v = l.inflate(res, null);
        }
        Log.e("item name ", sessionTypeModelList.get(position).sessions);

        TextView button = v.findViewById(R.id.btnCuisines);
        ImageView imageView = v.findViewById(R.id.imageView);

        button.setText(sessionTypeModelList.get(position).getSessions());
        int resId = sessionTypeModelList.get(position).getResId();
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        imageView.setImageDrawable(drawable);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SessionTypeWiseActivity.class);
                i.putExtra("cat", sessionTypeModelList.get(position).sessions);
                context.startActivity(i);
            }
        });

        return v;
    }

}
