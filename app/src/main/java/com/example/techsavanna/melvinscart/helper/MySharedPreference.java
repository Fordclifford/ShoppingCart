package com.example.techsavanna.melvinscart.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class MySharedPreference {

    private SharedPreferences prefs;

    private Context context;

    public MySharedPreference(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void addProductToTheCart(String product){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(Constants.PRODUCT_ID, product);
        edits.apply();
    }
    public void removeProductToTheCart(){
        SharedPreferences.Editor edits = prefs.edit();

     // System.out.println("system out:"+product);
      edits.clear();
      edits.commit();
       // System.out.println("system out:"+productid);
     // context.getSharedPreferences(Constants.PRODUCT_ID,1).edit().clear().commit();
      //  settings.edit().remove(String.valueOf(productid)).commit();
    }

    public String retrieveProductFromCart(){
        return prefs.getString(Constants.PRODUCT_ID, "");
    }

    public void addProductCount(int productCount){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(Constants.PRODUCT_COUNT, productCount);
        edits.apply();
    }

    public int retrieveProductCount(){
        return prefs.getInt(Constants.PRODUCT_COUNT, 0);
    }
}
