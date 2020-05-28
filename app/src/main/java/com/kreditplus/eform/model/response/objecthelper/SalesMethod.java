package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 26/12/16.
 */

public class SalesMethod {

    @SerializedName("source_application_id")
    private String sourceApplicationId;
    @SerializedName("source_application_name")
    private String sourceApplicationName;

    public String getSourceApplicationId() {
        return sourceApplicationId;
    }

    public void setSourceApplicationId(String sourceApplicationId) {
        this.sourceApplicationId = sourceApplicationId;
    }

    public String getSourceApplicationName() {
        return sourceApplicationName;
    }

    public void setSourceApplicationName(String sourceApplicationName) {
        this.sourceApplicationName = sourceApplicationName;
    }

    @Override
    public String toString() {
        return sourceApplicationName;
    }
}
