package com.example.mahara1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mahara1.database.DatabaseHelper;
import com.example.mahara1.database.SharedPrefManager;
import com.example.mahara1.restaurantOwner.data.Restaurant;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SessionTypeWiseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    List<Restaurant> list=new ArrayList<Restaurant>();
    ListView listView;
    public static String name,address;
    private SQLiteDatabase database;
    private List<String> categoryList;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
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
        navigationView.setNavigationItemSelectedListener(SessionTypeWiseActivity.this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        String cuisineCategory=getIntent().getStringExtra("cat");
        TextView txtViewName = navigationView.getHeaderView(0).findViewById(R.id.textViewName);
        txtViewName.setText(SharedPrefManager.getInstance(SessionTypeWiseActivity.this).getName());
        TextView txtViewEmail = navigationView.getHeaderView(0).findViewById(R.id.textViewEmail);
        txtViewEmail.setText(SharedPrefManager.getInstance(SessionTypeWiseActivity.this).getEmail());
       actionBar.setTitle(cuisineCategory+" - Available Session");
        listView=findViewById(R.id.listview);
        list = dbHelper.getRestaurantsByCuisine(cuisineCategory);
        setListData();
    }

    void setListData(){
        sessionAdapter restaurantAdapter=new sessionAdapter(SessionTypeWiseActivity.this,
                R.layout.list_session_booking,list);
        listView.setAdapter(restaurantAdapter);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SharedPrefManager.getInstance(SessionTypeWiseActivity.this).clearUserData();
                intent = new Intent(SessionTypeWiseActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}