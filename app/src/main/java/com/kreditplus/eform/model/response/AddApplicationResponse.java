package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 20/06/16.
 */
public class AddApplicationResponse {

    @SerializedName("ApplicationId")
    private String applicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
