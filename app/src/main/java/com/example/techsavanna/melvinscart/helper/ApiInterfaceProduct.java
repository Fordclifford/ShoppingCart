package com.example.techsavanna.melvinscart.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceProduct {
    @GET("getproducts.php")
    Call<List<Product>> getProduct(
            @Query("product_type") String product_type,
            @Query("key") String keyword
    );
}

