package com.example.mahara1.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mahara1.UserInfo;
import com.example.mahara1.restaurantOwner.data.Booking;
import com.example.mahara1.restaurantOwner.data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DineOutDB";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_NAME_CUSTOMER = "customers";

    private static final String TABLE_NAME_BOOKING = "seat_booking";

    private static final String COL_ID = "id";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_CITY = "city";
    private static final String COL_PROVINCE = "province";
    private static final String COL_PASSWORD = "password";


    // Column names for admin registration
    public static final String COL_OWNER_ID = "owner_id";
    public static final String COL_OWNER_NAME = "owner_name";
    public static final String COL_OWNER_CORPORATION_NUMBER = "corporation_number";
    public static final String COL_OWNER_ADDITIONAL_DETAILS = "additional_details";
    public static final String TABLE_NAME_RESTAURANT_OWNER = "restaurants";
    public static final String COL_RESTAURANT_NAME = "restaurant_name";
    public static final String COL_RESTAURANT_ADDRESS = "restaurant_address";
    public static final String COL_RESTAURANT_DESCRIPTION = "restaurant_description";
    public static final String COL_RESTAURANT_PHONE = "restaurant_phone";
    public static final String COL_RESTAURANT_EMAIL = "restaurant_email";
    public static final String COL_RESTAURANT_PASSWORD = "restaurant_password";
    public static final String R_LOGO = "logo";
    public static final String CUISINE_CATEGORY = "cuisineCategory";
    private static final String USER_TYPE = "user_type";

    // Column names for restaurant seat Booking
    public static final String COL_CUSTOMER_ID = "customer_id";
    public static final String COL_CUSTOMER_NAME = "customer_name";
    public static final String COL_CUSTOMER_EMAIL = "customer_email";
    public static final String COL_CUSTOMER_PHONE = "customer_phone";
    public static final String COL_OWNER_RESTURANT_ID = "owner_resturant_id";
    public static final String COL_OWNER_EMAIL = "owner_email";
    public static final String COL_OWNER_PHONE = "owner_phone";
    public static final String COL_CUSTOMER_ADDRESS="customer_address";
    // Create table statement for customers
    private static final String CREATE_TABLE_CUSTOMER =
            "CREATE TABLE " + TABLE_NAME_CUSTOMER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_FIRST_NAME + " TEXT," +
                    COL_LAST_NAME + " TEXT," +
                    COL_EMAIL + " TEXT," +
                    USER_TYPE + " TEXT," +
                    COL_PHONE + " TEXT," +
                    COL_OWNER_CORPORATION_NUMBER + " TEXT," +
                    COL_CITY + " TEXT," +
                    COL_PROVINCE + " TEXT," +
                    COL_PASSWORD + " TEXT" +
                    ")";

    String createOwnerTableQuery = "CREATE TABLE " + TABLE_NAME_RESTAURANT_OWNER + " ("
            + COL_OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_OWNER_NAME + " TEXT,"
            + COL_OWNER_CORPORATION_NUMBER + " TEXT,"
            + COL_OWNER_ADDITIONAL_DETAILS + " TEXT,"
            + COL_RESTAURANT_NAME + " TEXT,"
            + COL_RESTAURANT_ADDRESS + " TEXT,"
            + COL_RESTAURANT_DESCRIPTION + " TEXT,"
            + COL_RESTAURANT_PHONE + " TEXT,"
            + COL_RESTAURANT_EMAIL + " TEXT,"
            + COL_RESTAURANT_PASSWORD + " TEXT,"
            + R_LOGO + " TEXT,"
            + CUISINE_CATEGORY + " TEXT"
            + ")";
    private static final String CREATE_TABLE_SEAT_BOOKING =
            "CREATE TABLE " + TABLE_NAME_BOOKING + "(" +
                    COL_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_CUSTOMER_NAME + " TEXT," +
                    COL_CUSTOMER_EMAIL + " TEXT," +
                    COL_CUSTOMER_PHONE + " TEXT," +
                    COL_OWNER_RESTURANT_ID + " TEXT," +
                    COL_OWNER_EMAIL + " TEXT," +
                    COL_OWNER_PHONE + " TEXT" +
                    ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(createOwnerTableQuery);
        db.execSQL(CREATE_TABLE_SEAT_BOOKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER);
        // Create tables again
        onCreate(db);
    }

    //to insert student details into the database
    public long insertCustomer(String name, String email, String phone, String city, String state, String password, String userType, String corporationNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FIRST_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_OWNER_CORPORATION_NUMBER,corporationNumber);
        values.put(COL_CITY, city);
        values.put(COL_PROVINCE, state);
        values.put(COL_PASSWORD, password);
        values.put(USER_TYPE, userType); // Added user type

        long result = db.insert(TABLE_NAME_CUSTOMER, null, values);
       // db.close(); // Closing database connection
        return result;
    }

    // Method to check if the email already exists in the database
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME_CUSTOMER, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
     //  db.close();
        return count > 0;
    }

    public boolean checkCustomerLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + " = ?" + " AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME_CUSTOMER, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
      //  db.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public UserInfo getUserInfoByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_FIRST_NAME, COL_LAST_NAME, COL_PHONE, USER_TYPE, COL_OWNER_CORPORATION_NUMBER, COL_PASSWORD};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME_CUSTOMER, columns, selection, selectionArgs,
                null, null, null);
        UserInfo userInfo = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(COL_PHONE));
            String userType = cursor.getString(cursor.getColumnIndex(USER_TYPE));
            String corporationNumber = cursor.getString(cursor.getColumnIndex(COL_OWNER_CORPORATION_NUMBER));
            String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
            userInfo = new UserInfo(firstName, lastName, email, phone, userType, password,corporationNumber);
        }
        cursor.close();
      //  db.close();
        return userInfo;
    }
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        List<Restaurant> restaurantList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_RESTAURANT_OWNER,
                new String[]{COL_RESTAURANT_EMAIL, COL_OWNER_CORPORATION_NUMBER, COL_OWNER_ADDITIONAL_DETAILS,
                        COL_RESTAURANT_NAME, COL_RESTAURANT_ADDRESS, COL_RESTAURANT_DESCRIPTION,
                        COL_RESTAURANT_PHONE, R_LOGO, CUISINE_CATEGORY},
                CUISINE_CATEGORY + "=?",
                new String[]{cuisine},
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int emailIndex = cursor.getColumnIndex(COL_RESTAURANT_EMAIL);
                int corporationIndex = cursor.getColumnIndex(COL_OWNER_CORPORATION_NUMBER);
                int additionalDetailsIndex = cursor.getColumnIndex(COL_OWNER_ADDITIONAL_DETAILS);
                int nameIndex = cursor.getColumnIndex(COL_RESTAURANT_NAME);
                int addressIndex = cursor.getColumnIndex(COL_RESTAURANT_ADDRESS);
                int descriptionIndex = cursor.getColumnIndex(COL_RESTAURANT_DESCRIPTION);
                int phoneIndex = cursor.getColumnIndex(COL_RESTAURANT_PHONE);
                int logoUrlIndex = cursor.getColumnIndex(R_LOGO);
                int cuisineCategoryIndex = cursor.getColumnIndex(CUISINE_CATEGORY);

                if (emailIndex != -1 && corporationIndex != -1 && additionalDetailsIndex != -1 &&
                        nameIndex != -1 && addressIndex != -1 && descriptionIndex != -1 &&
                        phoneIndex != -1 && logoUrlIndex != -1 && cuisineCategoryIndex != -1) {
                    Restaurant restaurant = new Restaurant(
                            cursor.getString(emailIndex),
                            cursor.getString(corporationIndex),
                            cursor.getString(additionalDetailsIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(addressIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(phoneIndex),
                            cursor.getString(logoUrlIndex),
                            cursor.getString(cuisineCategoryIndex)
                    );
                    // Optionally, set additional properties on the restaurant object here
                    restaurantList.add(restaurant);
                } else {
                    // Handle the case where one or more columns aren't found
                    // This could involve logging an error, throwing an exception, etc.
                    // For example:
                    Log.e("DatabaseHelper", "One or more columns not found when querying the database.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return restaurantList;
    }
    // Method to insert customer details into the database
    public long insertCustomerBookingTable(String name, String email, String customerPhone, String ownerResturantId, String ownerEmail, String ownerPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CUSTOMER_NAME, name);
        values.put(COL_CUSTOMER_EMAIL, email);
        values.put(COL_CUSTOMER_PHONE, customerPhone);
        values.put(COL_OWNER_RESTURANT_ID, ownerResturantId);
        values.put(COL_OWNER_EMAIL, ownerEmail);
        values.put(COL_OWNER_PHONE, ownerPhone);
        // Inserting Row
        long result = db.insert(TABLE_NAME_BOOKING, null, values);
        // db.close(); // Closing database connection
        return result;
    }
    public List<Booking> getBookingsByRestaurantId(String restaurantId) {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COL_CUSTOMER_NAME,
                COL_CUSTOMER_EMAIL,
                COL_CUSTOMER_PHONE,
                COL_OWNER_RESTURANT_ID,
                COL_OWNER_EMAIL,
                COL_OWNER_PHONE
        };

        String selection = COL_OWNER_EMAIL + " = ?";
        String[] selectionArgs = { restaurantId };

        Cursor cursor = db.query(
                TABLE_NAME_BOOKING,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int customerNameIndex = cursor.getColumnIndex(COL_CUSTOMER_NAME);
                int customerEmailIndex = cursor.getColumnIndex(COL_CUSTOMER_EMAIL);
                int customerPhoneIndex = cursor.getColumnIndex(COL_CUSTOMER_PHONE);
                int ownerRestaurantIdIndex = cursor.getColumnIndex(COL_OWNER_RESTURANT_ID);
                int ownerEmailIndex = cursor.getColumnIndex(COL_OWNER_EMAIL);
                int ownerPhoneIndex = cursor.getColumnIndex(COL_OWNER_PHONE);

                if (customerNameIndex != -1 && customerEmailIndex != -1 && customerPhoneIndex != -1 &&
                        ownerRestaurantIdIndex != -1 && ownerEmailIndex != -1 && ownerPhoneIndex != -1) {
                    // Process the cursor data here
                    String customerName = cursor.getString(customerNameIndex);
                    String customerEmail = cursor.getString(customerEmailIndex);
                    String customerPhone = cursor.getString(customerPhoneIndex);
                    String ownerEmail = cursor.getString(ownerEmailIndex);
                    String ownerPhone = cursor.getString(ownerPhoneIndex);

                    // Create a new Booking object
                    Booking booking = new Booking(customerName, customerEmail, customerPhone, restaurantId, ownerEmail, ownerPhone);
                    bookings.add(booking);
                } else {
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return bookings;
    }
    public void deleteOwnerByCorporation(String corporation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_RESTAURANT_OWNER, COL_OWNER_CORPORATION_NUMBER + " = ?", new String[]{corporation});
        db.close();
    }
    public void updateOwnerByCorporation(String corporation, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_OWNER_NAME, newName);
        db.update(TABLE_NAME_RESTAURANT_OWNER, values, COL_OWNER_CORPORATION_NUMBER + " = ?", new String[]{corporation});
        db.close();
    }
    public Restaurant getRestaurantsByID(String corporation) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_RESTAURANT_OWNER,
                new String[]{COL_RESTAURANT_EMAIL, COL_OWNER_CORPORATION_NUMBER, COL_OWNER_ADDITIONAL_DETAILS,
                        COL_RESTAURANT_NAME, COL_RESTAURANT_ADDRESS, COL_RESTAURANT_DESCRIPTION,
                        COL_RESTAURANT_PHONE, R_LOGO, CUISINE_CATEGORY},
                COL_RESTAURANT_EMAIL + "=?",
                new String[]{corporation},
                null, null, null, null);
        Restaurant restaurant=null;
        if (cursor.moveToFirst()) {
            do {
                int emailIndex = cursor.getColumnIndex(COL_RESTAURANT_EMAIL);
                int corporationIndex = cursor.getColumnIndex(COL_OWNER_CORPORATION_NUMBER);
                int additionalDetailsIndex = cursor.getColumnIndex(COL_OWNER_ADDITIONAL_DETAILS);
                int nameIndex = cursor.getColumnIndex(COL_RESTAURANT_NAME);
                int addressIndex = cursor.getColumnIndex(COL_RESTAURANT_ADDRESS);
                int descriptionIndex = cursor.getColumnIndex(COL_RESTAURANT_DESCRIPTION);
                int phoneIndex = cursor.getColumnIndex(COL_RESTAURANT_PHONE);
                int logoUrlIndex = cursor.getColumnIndex(R_LOGO);
                int cuisineCategoryIndex = cursor.getColumnIndex(CUISINE_CATEGORY);
                Log.e("DB_DEBUG", "emailIndex: " + emailIndex + ", corporationIndex: " + corporationIndex + ", additionalDetailsIndex: " + additionalDetailsIndex + ", nameIndex: " + nameIndex + ", addressIndex: " + addressIndex + ", descriptionIndex: " + descriptionIndex + ", phoneIndex: " + phoneIndex + ", logoUrlIndex: " + logoUrlIndex + ", cuisineCategoryIndex: " + cuisineCategoryIndex);

                if (emailIndex != -1 && corporationIndex != -1 && additionalDetailsIndex != -1 &&
                        nameIndex != -1 && addressIndex != -1 && descriptionIndex != -1 &&
                        phoneIndex != -1 && logoUrlIndex != -1 && cuisineCategoryIndex != -1) {
                    Log.e("DB_DEBUG", "emailIndex: " + cursor.getString(emailIndex) + ", corporationIndex: " + cursor.getString(corporationIndex) + ", additionalDetailsIndex: " +  cursor.getString(additionalDetailsIndex) + ", nameIndex: " + cursor.getString(nameIndex) + ", addressIndex: " + cursor.getString(nameIndex) + ", descriptionIndex: " );

                    restaurant = new Restaurant(
                            cursor.getString(emailIndex),
                            cursor.getString(corporationIndex),
                            cursor.getString(additionalDetailsIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(addressIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(phoneIndex),
                            cursor.getString(logoUrlIndex),
                            cursor.getString(cuisineCategoryIndex)
                    );
                    // Optionally, set additional properties on the restaurant object here
                } else {
                    // Handle the case where one or more columns aren't found
                    // This could involve logging an error, throwing an exception, etc.
                    // For example:
                    Log.e("DatabaseHelper", "One or more columns not found when querying the database.");
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return restaurant;
    }
}
