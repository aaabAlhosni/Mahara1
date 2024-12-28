package com.example.mahara1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.sessionListActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize EditText fields and buttons
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set onClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login when login button is clicked
                loginUser();
            }
        });

        // Set onClickListener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to registration activity when register button is clicked
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        if (checkStoragePermission()) {
            // Permission is already granted, proceed with your operations
        } else {
            // Permission is not granted, request it
            requestStoragePermission();
        }
    }

    // handle customer login
    private void loginUser() {
        // Get user input from EditText fields
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if any field is blank
        if (username.isEmpty() || password.isEmpty()) {
            // Show a toast message if any field is blank
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Check if credentials are correct
            boolean isValidLogin = dbHelper.checkCustomerLogin(username, password);

            if (isValidLogin) {
                // Show a toast message for successful login
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                UserInfo userInfo = dbHelper.getUserInfoByEmail(usernameEditText.getText().toString().trim());

                SharedPrefManager.getInstance(LoginActivity.this)
                        .saveUserInfo(userInfo.getName(),
                                usernameEditText.getText().toString().trim(),
                                userInfo.getPhone(),userInfo.getUserType());
                SharedPrefManager.getInstance(LoginActivity.this).setCorporationNumber(userInfo.getCorporationNumber());

                // Navigate to HomeActivity upon successful login
                Intent intent;
                if (userInfo.getUserType().equals("restaurant")) {
                    // Navigate to UserHomeActivity for customers
                    intent = new Intent(LoginActivity.this, sessionListActivity.class);
                } else {
                    // Navigate to sessionListActivity for admin
                    intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                }
                startActivity(intent);
            } else {
                // Show a toast message for login failure
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
    }

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        }
    }

}
