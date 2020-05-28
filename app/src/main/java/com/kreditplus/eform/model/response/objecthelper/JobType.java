package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Iwan Nurdesa on 14/07/16.
 */
public class JobType {
    
    @SerializedName("ProfessionID")
    private String ProfessionID;
    @SerializedName("JobTypeID")
    private String JobTypeID;
    @SerializedName("Description")
    private String Description;
    @SerializedName("IsActive")
    private String IsActive;

    public String getProfessionID() {
        return ProfessionID;
    }

    public void setProfessionID(String professionID) {
        ProfessionID = professionID;
    }

    public String getJobTypeID() {
        return JobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        JobTypeID = jobTypeID;
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

    public JobType(String professionID, String jobTypeID, String description) {
        ProfessionID = professionID;
        JobTypeID = jobTypeID;
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }
}
