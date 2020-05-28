package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuEdu {

    @SerializedName("EducationID")
    private String educationId;

    @SerializedName("EducationName")
    private String educationName;

    @Override
    public String toString() {
        return educationName;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }
}
