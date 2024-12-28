package com.example.mahara1.database;

import static com.example.mahara1.database.DatabaseHelper.COL_OWNER_ADDITIONAL_DETAILS;
import static com.example.mahara1.database.DatabaseHelper.COL_OWNER_CORPORATION_NUMBER;
import static com.example.mahara1.database.DatabaseHelper.COL_RESTAURANT_ADDRESS;
import static com.example.mahara1.database.DatabaseHelper.COL_RESTAURANT_DESCRIPTION;
import static com.example.mahara1.database.DatabaseHelper.COL_RESTAURANT_EMAIL;
import static com.example.mahara1.database.DatabaseHelper.COL_RESTAURANT_NAME;
import static com.example.mahara1.database.DatabaseHelper.COL_RESTAURANT_PHONE;
import static com.example.mahara1.database.DatabaseHelper.CUISINE_CATEGORY;
import static com.example.mahara1.database.DatabaseHelper.R_LOGO;
import static com.example.mahara1.database.DatabaseHelper.TABLE_NAME_RESTAURANT_OWNER;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mahara1.restaurantOwner.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class AddRestaurantRegistrationManager {

    private DatabaseHelper dbHelper;

    public AddRestaurantRegistrationManager(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long AddRestaurant(String restaurantEmail, String corporationNumber, String additionalDetails,
                              String restaurantName, String restaurantAddress, String restaurantDescription,
                              String restaurantPhone,  String logoURI,String cuisineCategory) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RESTAURANT_EMAIL, restaurantEmail);
        values.put(COL_OWNER_CORPORATION_NUMBER, corporationNumber);
        values.put(COL_OWNER_ADDITIONAL_DETAILS, additionalDetails);
        values.put(COL_RESTAURANT_NAME, restaurantName);
        values.put(COL_RESTAURANT_ADDRESS, restaurantAddress);
        values.put(COL_RESTAURANT_DESCRIPTION, restaurantDescription);
        values.put(COL_RESTAURANT_PHONE, restaurantPhone);
        values.put(R_LOGO, logoURI);
        values.put(CUISINE_CATEGORY, cuisineCategory);
        long newRowId = db.insert(TABLE_NAME_RESTAURANT_OWNER, null, values);
        return newRowId;
    }


    @SuppressLint("Range")
    public List<Restaurant> getRestaurants(String ownerEmail) {
        List<Restaurant> restaurants = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = COL_RESTAURANT_EMAIL + " = ?";
        String[] selectionArgs = {ownerEmail};
        Cursor cursor = db.query(TABLE_NAME_RESTAURANT_OWNER, null, selection, selectionArgs, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String restaurantEmail = cursor.getString(cursor.getColumnIndex(COL_RESTAURANT_EMAIL));
                    String corporationNumber = cursor.getString(cursor.getColumnIndex(COL_OWNER_CORPORATION_NUMBER));
                    String additionalDetails = cursor.getString(cursor.getColumnIndex(COL_OWNER_ADDITIONAL_DETAILS));
                    String restaurantName = cursor.getString(cursor.getColumnIndex(COL_RESTAURANT_NAME));
                    String restaurantAddress = cursor.getString(cursor.getColumnIndex(COL_RESTAURANT_ADDRESS));
                    String restaurantDescription = cursor.getString(cursor.getColumnIndex(COL_RESTAURANT_DESCRIPTION));
                    String restaurantPhone = cursor.getString(cursor.getColumnIndex(COL_RESTAURANT_PHONE));
                    String logoURI = cursor.getString(cursor.getColumnIndex(R_LOGO)); // Fetching the logo URI
                    String cuisineCategory = cursor.getString(cursor.getColumnIndex(CUISINE_CATEGORY)); // Fetching the logo URI
                    // Create a new object with the retrieved data
                    Restaurant restaurant = new Restaurant(restaurantEmail,corporationNumber , additionalDetails,
                            restaurantName, restaurantAddress, restaurantDescription,
                            restaurantPhone, logoURI,cuisineCategory);
                    restaurants.add(restaurant);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return restaurants;
    }

    public long UpdateRestaurant(String restaurantEmail, String corporationNumber, String additionalDetails,
                              String restaurantName, String restaurantAddress, String restaurantDescription,
                              String restaurantPhone,  String logoURI,String cuisineCategory) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RESTAURANT_EMAIL, restaurantEmail);
        values.put(COL_OWNER_CORPORATION_NUMBER, corporationNumber);
        values.put(COL_OWNER_ADDITIONAL_DETAILS, additionalDetails);
        values.put(COL_RESTAURANT_NAME, restaurantName);
        values.put(COL_RESTAURANT_ADDRESS, restaurantAddress);
        values.put(COL_RESTAURANT_DESCRIPTION, restaurantDescription);
        values.put(COL_RESTAURANT_PHONE, restaurantPhone);
        values.put(R_LOGO, logoURI);
        values.put(CUISINE_CATEGORY, cuisineCategory);
        long newRowId = db.update(TABLE_NAME_RESTAURANT_OWNER, values, COL_RESTAURANT_EMAIL + " = ?", new String[]{restaurantEmail});;
        return newRowId;
    }
}