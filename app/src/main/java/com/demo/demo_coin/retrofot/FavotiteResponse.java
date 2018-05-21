package com.demo.demo_coin.retrofot;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class FavotiteResponse {

/*
    @SerializedName("data")
    List<DataResponse> data;


    @SerializedName("metadata")
    List<MetadataResponse> metadata;*/

    @SerializedName("data")
    DataResponse data;


    @SerializedName("metadata")
    MetadataResponse metadata;

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }

    public MetadataResponse getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataResponse metadata) {
        this.metadata = metadata;
    }
}
