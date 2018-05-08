package com.demo.demo_coin.retrofot;

import com.google.gson.annotations.SerializedName;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class DataResponse {


    @SerializedName("id")
    Long id;

    @SerializedName("name")
    String name;

    @SerializedName("symbol")
    String symbol;

    @SerializedName("website_slug")
    String website_slug;


    @SerializedName("rank")
    Long rank;

    @SerializedName("circulating_supply")
    Double circulating_supply;

    @SerializedName("total_supply")
    Double total_supply;

    @SerializedName("max_supply")
    Double max_supply;

   /* @SerializedName("quotes")
    List<Quotes> quotes;*/


    @SerializedName("quotes")
    Quotes quotes;

    @SerializedName("last_updated")
    Long last_updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite_slug() {
        return website_slug;
    }

    public void setWebsite_slug(String website_slug) {
        this.website_slug = website_slug;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Double getCirculating_supply() {
        return circulating_supply;
    }

    public void setCirculating_supply(Double circulating_supply) {
        this.circulating_supply = circulating_supply;
    }

    public Double getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(Double total_supply) {
        this.total_supply = total_supply;
    }

    public Double getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(Double max_supply) {
        this.max_supply = max_supply;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    public Long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Long last_updated) {
        this.last_updated = last_updated;
    }
}
