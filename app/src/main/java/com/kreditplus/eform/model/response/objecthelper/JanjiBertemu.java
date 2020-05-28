package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apc-lap012 on 10/05/17.
 */

public class JanjiBertemu {

    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("application_id")
    private List<String> appIdList;
    @SerializedName("handphone")
    private String handphone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<String> appIdList) {
        this.appIdList = appIdList;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }
}
