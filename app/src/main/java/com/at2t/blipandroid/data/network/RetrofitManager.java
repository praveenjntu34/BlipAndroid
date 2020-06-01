package com.at2t.blipandroid.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager retrofitService;
    private final ApiInterface apiInterface;

    private RetrofitManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://seventhharmony.com:3400/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }


    public static RetrofitManager getInstance(){
        if(retrofitService == null){
            retrofitService = new RetrofitManager();
        }
        return retrofitService;
    }

    public ApiInterface getApiInterface(){
        return apiInterface;
    }

}

