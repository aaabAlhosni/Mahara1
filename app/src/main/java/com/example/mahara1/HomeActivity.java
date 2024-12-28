package com.example.mahara1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onCustomerLoginClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
