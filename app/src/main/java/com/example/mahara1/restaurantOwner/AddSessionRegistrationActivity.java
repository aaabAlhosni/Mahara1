package com.example.mahara1.restaurantOwner;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mahara1.BookingActivity;
import com.example.mahara1.LoginActivity;
import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.R;
import com.example.mahara1.database.AddRestaurantRegistrationManager;
import com.example.mahara1.database.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class AddSessionRegistrationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText restaurantCityEditText, restaurantStateEditText, corporationNumEditText,
            restaurantNameEditText, restaurantDescriptionEditText;
    private Button restaurantRegisterButton;
    TextView restaurantLogoButton;

    private AddRestaurantRegistrationManager registrationManager;
    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking an image
    private ImageView restaurantLogoImageView;
    private String selectedImageUri; // Store the URI of the selected image
    private Spinner restaurantTypeSpinner; // Store the URI of the selected image
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session_registration);
        Toolbar toolbar = findViewById(R.id.toolbarNew);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set custom drawer icon
            Drawable drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon);
            actionBar.setHomeAsUpIndicator(drawerIcon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(AddSessionRegistrationActivity.this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        registrationManager = new AddRestaurantRegistrationManager(dbHelper);

        // Initialize EditText fields...
        restaurantTypeSpinner = findViewById(R.id.sessionTypeSpinner);
        restaurantCityEditText = findViewById(R.id.sessionlocationEditText);
        restaurantStateEditText = findViewById(R.id.timingStateEditText);
        corporationNumEditText = findViewById(R.id.capacityNumEditText);
        restaurantNameEditText = findViewById(R.id.sessionNameEditText);
        restaurantLogoButton = findViewById(R.id.addthumbnailButton);
        restaurantDescriptionEditText = findViewById(R.id.Description1EditText);
        restaurantLogoImageView = findViewById(R.id.imgLogo);

        // Initialize Buttons...
        restaurantRegisterButton = findViewById(R.id.sessionRegisterButton);

        restaurantLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        // Set click listener for the Register Button
        restaurantRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    String corporationNumber = corporationNumEditText.getText().toString().trim();
                    String restaurantName = restaurantNameEditText.getText().toString().trim();
                    String cuisineCategory = restaurantTypeSpinner.getSelectedItem().toString();
                    String restaurantDescription = restaurantDescriptionEditText.getText().toString().trim();
                    String restaurantEmail = SharedPrefManager.getInstance(AddSessionRegistrationActivity.this).getEmail();
                    String restaurantPhone = SharedPrefManager.getInstance(AddSessionRegistrationActivity.this).getPhone();
                    String city = restaurantCityEditText.getText().toString().trim();
                    String state = "Muscat";
                    String country = "Oman";
                    String restaurantAddress = restaurantName + ", " + city + ", " + state + ", " + country;
                    long newRowId = registrationManager.AddRestaurant(restaurantEmail, corporationNumber, "",
                            restaurantName, restaurantAddress, restaurantDescription, restaurantPhone, selectedImageUri, cuisineCategory
                    );
SharedPrefManager.getInstance(getApplicationContext()).setCorporationNumber(corporationNumber);

                    if (newRowId != -1) {
                        Toast.makeText(AddSessionRegistrationActivity.this,
                                "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddSessionRegistrationActivity.this, sessionListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddSessionRegistrationActivity.this,
                                "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddSessionRegistrationActivity.this,
                            "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        // Check if all required fields are filled out
        if (restaurantNameEditText.getText().toString().isEmpty() ||
                restaurantCityEditText.getText().toString().isEmpty() ||
                restaurantDescriptionEditText.getText().toString().isEmpty() ||
                restaurantStateEditText.getText().toString().isEmpty() ||
                restaurantTypeSpinner.getSelectedItem().toString().isEmpty()) {
            // If any required field is empty, return false
            return false;
        }

        // Additional validation logic if needed...

        // If all required fields are filled out, return true
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri uri = data.getData();
            selectedImageUri = uri.toString();
            // Display the selected image in the ImageView
            Log.e("image uri ", selectedImageUri);
            restaurantLogoImageView.setImageURI(uri);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final int HOME_ID = R.id.nav_home;

        switch (id) {
            case R.id.nav_home:
                Intent intent = new Intent(this, sessionListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_add_session:
                 intent = new Intent(this, AddSessionRegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.booking:
                intent = new Intent(this, BookingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SharedPrefManager.getInstance(AddSessionRegistrationActivity.this).clearUserData();
                intent = new Intent(AddSessionRegistrationActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
                startActivity(intent);
                break;
            default:
                // Handle other cases
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
