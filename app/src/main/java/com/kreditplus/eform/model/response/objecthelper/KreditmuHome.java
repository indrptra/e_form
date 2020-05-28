package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuHome {

    @SerializedName("HomeStatusID")
    private String homeId;

    @SerializedName("HomeStatusName")
    private String homeName;

    @Override
    public String toString() {
        return homeName;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
