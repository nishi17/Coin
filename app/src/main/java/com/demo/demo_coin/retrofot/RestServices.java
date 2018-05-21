package com.demo.demo_coin.retrofot;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public interface RestServices {


    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @GET(API.Base_Url)
    Call<JsonObject> AllCoins();

    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @GET
    Call<FavotiteResponse> callApi(@Url String s);


}
