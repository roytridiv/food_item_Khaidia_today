package com.example.food_item;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("order_summary.php")
    Call<ResponseBody> fetchData(
            @Field("DATE") String date
    );

}
