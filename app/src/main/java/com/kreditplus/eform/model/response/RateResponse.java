package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 07/08/17.
 */

public class RateResponse {

    @SerializedName("percentage")
    private double rate;

    @SerializedName("percentageEformUsage")
    private int penggunaan;

    @SerializedName("lastEditDate")
    private String date;

    public double getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getPenggunaan() {
        return penggunaan;
    }

    public void setPenggunaan(int penggunaan) {
        this.penggunaan = penggunaan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
