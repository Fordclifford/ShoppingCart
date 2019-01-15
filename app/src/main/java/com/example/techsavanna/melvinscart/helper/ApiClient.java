package com.example.techsavanna.melvinscart.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.techsavanna.melvinscart.app.AppConfig.BASE_URL;

public class ApiClient {

    public static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if (retrofit==null){

retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
