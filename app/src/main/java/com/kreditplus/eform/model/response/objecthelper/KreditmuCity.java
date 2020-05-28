package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuCity {

    @SerializedName("ResidenceCity")
    private String residanceCity;

    @Override
    public String toString() {
        return residanceCity;
    }

    public String getResidanceCity() {
        return residanceCity;
    }

    public void setResidanceCity(String residanceCity) {
        this.residanceCity = residanceCity;
    }
}
