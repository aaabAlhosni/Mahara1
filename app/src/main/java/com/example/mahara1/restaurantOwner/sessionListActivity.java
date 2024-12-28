package com.example.mahara1.restaurantOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.mahara1.BookingActivity;
import com.example.mahara1.LoginActivity;
import com.example.mahara1.database.AddRestaurantRegistrationManager;
import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.R;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.data.Restaurant;
import com.example.mahara1.restaurantOwner.adapter.RestaurantListAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class sessionListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private RecyclerView recyclerView;
    private RestaurantListAdapter adapter;
    private Button addRestaurantButton,btnLogout,btnBooking;
    private DrawerLayout drawer;

    private String ownerEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_session_list);
        Toolbar toolbar = findViewById(R.id.toolbar1);
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
        navigationView.setNavigationItemSelectedListener(sessionListActivity.this);


        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);

        // Get owner's email from shared preferences
        ownerEmail = SharedPrefManager.getInstance(this).getEmail();

        // Set up RecyclerView

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set click listener for add restaurant button
        checkAndRequestPermissions();

    }
    private List<Restaurant> loadRestaurants() {
        // Query database to fetch restaurants based on owner's email
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return new AddRestaurantRegistrationManager(dbHelper).getRestaurants(ownerEmail);
    }
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            setupRecyclerView();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
                SharedPrefManager.getInstance(sessionListActivity.this).clearUserData();
                intent = new Intent(sessionListActivity.this, LoginActivity.class);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, setup the RecyclerView
                setupRecyclerView();

            } else {
                // Permission denied, handle the failure scenario
            }
        }
    }

    private void setupRecyclerView() {
        loadRestaurants();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantListAdapter(this, loadRestaurants());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();}
}
