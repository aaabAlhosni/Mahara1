package com.example.mahara1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.sessionListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve user information from SharedPreferences
        String userType = SharedPrefManager.getInstance(this).getUserType();
        String userEmail = SharedPrefManager.getInstance(this).getEmail();

        // Check if user email is not blank
        if (!userEmail.isEmpty()) {
            // Check if the user is a customer
            if (userType.equals("session")) {
                // Navigate to UserHomeActivity for customers
                Intent intent = new Intent(MainActivity.this, sessionListActivity.class);
                startActivity(intent);
            } else {
                // Navigate to sessionListActivity for Admin
                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        } else {
            // If user email is blank, navigate to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Finish the current activity
        finish();
    }

}
