package com.demo.demo_coin.retrofot;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class RestAdapter {


    public static Retrofit retrofit;
    public static RestServices exStuffServices;


    public final static RestServices getExStuffServices() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        if (RestAdapter.exStuffServices == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(API.Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            ;
            RestAdapter.exStuffServices = retrofit.create(RestServices.class);
            return RestAdapter.exStuffServices;
        }
        return RestAdapter.exStuffServices;

    }
}
