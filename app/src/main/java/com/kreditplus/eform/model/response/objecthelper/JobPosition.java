package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 14/07/16.
 */
public class JobPosition {

    @SerializedName("JobTypeID")
    private String JobTypeID;
    @SerializedName("JobPositionID")
    private String JobPositionID;
    @SerializedName("Description")
    private String Description;
    @SerializedName("IsActive")
    private String IsActive;

    public JobPosition(String jobTypeID, String jobPositionID, String description) {
        JobTypeID = jobTypeID;
        JobPositionID = jobPositionID;
        Description = description;
    }

    public String getJobTypeID() {
        return JobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        JobTypeID = jobTypeID;
    }

    public String getJobPositionID() {
        return JobPositionID;
    }

    public void setJobPositionID(String jobPositionID) {
        JobPositionID = jobPositionID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    @Override
    public String toString() {
        return Description;
    }
}
