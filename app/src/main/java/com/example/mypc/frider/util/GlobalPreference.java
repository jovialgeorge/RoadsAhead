package com.example.mypc.frider.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MyPc on 09-09-2017.
 */

public class GlobalPreference {


    private SharedPreferences prefs;
    private Context context;
    SharedPreferences.Editor editor;

    public GlobalPreference(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void addIP(String ip) {
        editor.putString(Constants.IP, ip);
        editor.apply();

    }

    public String RetriveIP() {
        return prefs.getString(Constants.IP, "");
    }

    public void SaveCredentials(String name, String password) {
        editor.putString(Constants.NAME,name);
        editor.putString(Constants.PASSWORD, password);
        editor.commit();
    }


    public String getName() {
        String phone = prefs.getString(Constants.NAME, "");
        return phone;
    }

    public String getPassword() {
        String password = prefs.getString(Constants.PASSWORD, "");
        return password;
    }


    public void addProductToCart(String product) {
        editor = prefs.edit();
        editor.putString(Constants.PRODUCT_ID, product);
        editor.apply();

    }

    public String RetriveProductFromCart() {
        return prefs.getString(Constants.PRODUCT_ID, "");
    }

    public void AddProductCount(int productCount) {

        editor = prefs.edit();
        editor.putInt(Constants.PRODUCT_COUNT, productCount);
        editor.apply();

    }

    public int retrieveProductCount() {

        return prefs.getInt(Constants.PRODUCT_COUNT, 0);
    }

}
