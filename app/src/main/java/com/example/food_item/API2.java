package com.example.food_item;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API2 {

    @FormUrlEncoded
    @POST("order_details.php")
    Call<ResponseBody> fetchData(
            @Field("orderID") String order_id
    );
}
