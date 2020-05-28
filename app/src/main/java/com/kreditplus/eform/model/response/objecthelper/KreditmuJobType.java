package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuJobType {

    @SerializedName("JobTypeID")
    private String jobTypeId;

    @SerializedName("JobTypeName")
    private String jobTypeName;

    @Override
    public String toString() {
        return jobTypeName;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }
}
