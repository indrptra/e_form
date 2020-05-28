package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 14/07/16.
 */
public class Industri {

    @SerializedName("IndustryTypeID")
    private String id;
    @SerializedName("Name")
    private String description;
    @SerializedName("isActive")
    private String isActive;

    public Industri(String id, String description){
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getdescription() {
        return description;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return description;
    }
}
