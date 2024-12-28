package com.example.mahara1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.sessionListActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstNameEditText, emailEditText, phoneEditText, cityEditText, addressProvinceEditText, passwordEditText;
    Button registerButton,loginButton;
    DatabaseHelper dbHelper;
    private RadioGroup userTypeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize EditText fields and register button
        firstNameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        cityEditText = findViewById(R.id.addressCityEditText);
        addressProvinceEditText = findViewById(R.id.addressProvinceEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        userTypeRadioGroup = findViewById(R.id.userTypeRadioGroup);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set onClickListener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration when register button is clicked
                registerCustomer();
            }
        });
    }

    // Method to register customer
    private void registerCustomer() {
        // Get user input from EditText fields
        String name = firstNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String province = addressProvinceEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check which radio button is selected
        int selectedRadioButtonId = userTypeRadioGroup.getCheckedRadioButtonId();
        String userType = "";
        if (selectedRadioButtonId == R.id.studentRadioButton) {
            userType = "student";
        } else if (selectedRadioButtonId == R.id.adminRadioButton) {
            userType = "restaurant";
        }

        // Check if any field is blank
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(city) &&
                !TextUtils.isEmpty(province) && !TextUtils.isEmpty(userType)) {
            // Check if email already exists
            if (!dbHelper.isEmailExists(email)) {
                // Insert customer into the database
                long result = dbHelper.insertCustomer(name, email, phone, city, province, password, userType,"");
                if (result != -1) {
                    // Registration successful
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Handle navigation to the next screen

                    SharedPrefManager.getInstance(RegistrationActivity.this)
                            .saveUserInfo(name, email, phone,userType);

                    // Navigate to HomeActivity upon successful login
                    if (userType.equals("session")) {
                        // Navigate to UserHomeActivity for customers
                        Intent intent = new Intent(RegistrationActivity.this, sessionListActivity.class);
                        startActivity(intent);
                    } else {
                        // Navigate to RestaurantListActivity for restaurant owners
                        Intent intent = new Intent(RegistrationActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                    }

                } else {
                    // Registration failed
                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Email already exists
                Toast.makeText(RegistrationActivity.this, "Email already registered, please use a different email", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Required fields are empty
            Toast.makeText(RegistrationActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }
        }
    }
