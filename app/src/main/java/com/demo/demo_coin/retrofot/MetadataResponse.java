package com.demo.demo_coin.retrofot;

import com.google.gson.annotations.SerializedName;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class MetadataResponse {


    @SerializedName("timestamp")
    Long timestamp;

    @SerializedName("error")
    String error;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
