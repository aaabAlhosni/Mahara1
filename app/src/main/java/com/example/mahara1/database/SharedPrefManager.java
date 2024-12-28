package com.example.mahara1.database;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String USER_TYPE = "user_type";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_CORPORATE_NUMBER = "pan_number";

    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUserInfo(String name, String email, String phone,String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(USER_TYPE, userType);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPhone() {
        return sharedPreferences.getString(KEY_PHONE, "");
    }
    public String getCorporationNumber() {
        return sharedPreferences.getString(KEY_CORPORATE_NUMBER, "");
    }
    public String setCorporationNumber(String corporationNumber) {
        return sharedPreferences.getString(KEY_CORPORATE_NUMBER, corporationNumber);
    }
    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE, "customer");
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
