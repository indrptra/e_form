package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuMarital {

    @SerializedName("MaritalStatusID")
    private String maritalId;

    @SerializedName("MaritalStatusName")
    private String maritalName;

    @Override
    public String toString() {
        return maritalName;
    }

    public String getMaritalId() {
        return maritalId;
    }

    public void setMaritalId(String maritalId) {
        this.maritalId = maritalId;
    }

    public String getMaritalName() {
        return maritalName;
    }

    public void setMaritalName(String maritalName) {
        this.maritalName = maritalName;
    }
}
