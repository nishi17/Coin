package com.demo.demo_coin.retrofot;

import com.google.gson.annotations.SerializedName;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class Quotes {

/*    @SerializedName("USD")
    List<USDresponse> USD;

    @SerializedName("INR")
    List<INResponse> INR;*/


    @SerializedName("USD")
    USDresponse USD;

    @SerializedName("INR")
    INResponse INR;


    public USDresponse getUSD() {
        return USD;
    }

    public void setUSD(USDresponse USD) {
        this.USD = USD;
    }

    public INResponse getINR() {
        return INR;
    }

    public void setINR(INResponse INR) {
        this.INR = INR;
    }
}
