package com.example.techsavanna.melvinscart.helper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIPostOrderService {
    @POST("addSaleOrder.php")
    @FormUrlEncoded
    Call<PostOrder> savePost(@Field("subject") String subject,
                             @Field("customerno") String customerno,
                             @Field("total") String total,
                             @Field("id") long userId);

}
