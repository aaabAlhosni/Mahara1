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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mahara1.database.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    List<SessionModel> list=new ArrayList<SessionModel>();
    ListView listView;
     DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        listView=findViewById(R.id.listview);
        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set custom drawer icon
            Drawable drawerIcon = ContextCompat.getDrawable(this, R.drawable.drawer_icon);
            actionBar.setHomeAsUpIndicator(drawerIcon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(UserHomeActivity.this);
        TextView txtViewName = navigationView.getHeaderView(0).findViewById(R.id.textViewName);
        txtViewName.setText(SharedPrefManager.getInstance(UserHomeActivity.this).getName());
        TextView txtViewEmail = navigationView.getHeaderView(0).findViewById(R.id.textViewEmail);
        txtViewEmail.setText(SharedPrefManager.getInstance(UserHomeActivity.this).getEmail());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        list.add(new SessionModel("Training 1",1,R.drawable.sessiontype2));
        list.add(new SessionModel("Training 2",1,R.drawable.sessiontype));
        list.add(new SessionModel("Training 3",1,R.drawable.sessiontype3));
        list.add(new SessionModel("Training 4",1,R.drawable.sessiontype4));

        TypesAdapter restaurantAdapter=new TypesAdapter(UserHomeActivity.this,
                R.layout.list_session_type,list);
        Log.e("list size ",list.size()+"");
        listView.setAdapter(restaurantAdapter);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final int HOME_ID = R.id.nav_home;

        switch (id) {
            case R.id.nav_home:
                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SharedPrefManager.getInstance(UserHomeActivity.this).clearUserData();
                intent = new Intent(UserHomeActivity.this, LoginActivity.class);
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