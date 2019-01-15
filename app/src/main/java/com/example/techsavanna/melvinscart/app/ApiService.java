package com.example.techsavanna.melvinscart.app;

import com.example.techsavanna.melvinscart.entities.Orders;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public  interface ApiService {
    @FormUrlEncoded
    @POST("/melvinscart/retrofit/index.php")
   Call<Orders>  insertData(@Field("total") String total, @Field("customerno") String customerno, @Field("subject") String subject);

}
