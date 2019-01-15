package com.example.techsavanna.melvinscart.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.techsavanna.melvinscart.app.AppConfig.BASE_URL;

public class ApiProduct {
    public static Retrofit retrofit;

    public static Retrofit getApiProduct(){
        if (retrofit==null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
