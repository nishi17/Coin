package com.demo.demo_coin.retrofot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public interface RestServices {


    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @GET(API.Base_Url)
    Call<FavotiteResponse> AllCoins();

    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @GET(/*"/{id}/+" */API.MyFlyer)
    Call<FavotiteResponse> callApi(/*@Path("id") int longs*/);


}
