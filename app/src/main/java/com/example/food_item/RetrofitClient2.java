package com.example.food_item;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    private static  final String BASE_URL = "https://api.khaidaitoday.com/manage_order/";

    private static RetrofitClient2 mInstance;

    private Retrofit retrofit;

    private RetrofitClient2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient2 getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient2();
        }
        return mInstance;
    }

    public API2 getApi(){
        return retrofit.create(API2.class);
    }
}
