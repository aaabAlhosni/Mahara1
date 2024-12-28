package com.example.mahara1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.AddSessionRegistrationActivity;
import com.example.mahara1.restaurantOwner.sessionListActivity;
import com.example.mahara1.restaurantOwner.data.Booking;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class BookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private BookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = findViewById(R.id.toolbarNew);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // drawer icon
            Drawable drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon);
            actionBar.setHomeAsUpIndicator(drawerIcon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(BookingActivity.this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        DatabaseHelper dbHelper=new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper.getBookingsByRestaurantId(SharedPrefManager.getInstance(this).getCorporationNumber());
        setupRecyclerView();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view for item clicks.
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
                SharedPrefManager.getInstance(BookingActivity.this).clearUserData();
                intent = new Intent(BookingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:

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
    protected void onResume() {
        super.onResume();
        loadRestaurants();
        adapter.notifyDataSetChanged();
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
    private List<Booking> loadRestaurants() {
        // for database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.getBookingsByRestaurantId(SharedPrefManager.getInstance(BookingActivity.this).getEmail());
    }
    private void setupRecyclerView() {
        loadRestaurants();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingAdapter(this, loadRestaurants());
        recyclerView.setAdapter(adapter);    }
}