package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 29/07/16.
 */
public class Notifikasi {

    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private String time;
    @SerializedName("application_id")
    private List<String> appIdList;
    @SerializedName("data")
    DataRetake dataRetake;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public DataRetake getDataRetake() {
        return dataRetake;
    }

    public void setDataRetake(DataRetake dataRetake) {
        this.dataRetake = dataRetake;
    }
}
