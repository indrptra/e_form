package com.kreditplus.eform.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 03/08/16.
 */
public class BidResponse {

    @SerializedName("application_id")
    private String applicationID;

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }
}
