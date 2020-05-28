package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuJobPosition {

    @SerializedName("JobPositionID")
    private String jobPositionId;

    @SerializedName("JobPositionName")
    private String jobPositionName;

    @Override
    public String toString() {
        return jobPositionName;
    }

    public String getJobPositionName() {
        return jobPositionName;
    }

    public void setJobPositionName(String jobPositionName) {
        this.jobPositionName = jobPositionName;
    }

    public String getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(String jobPositionId) {
        this.jobPositionId = jobPositionId;
    }
}
