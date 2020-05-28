package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tblNewProfJobPosition")
public class TblNewProfJobPosition {

    @DatabaseField(columnName = "JobTypeID")
    private String JobTypeID;
    @DatabaseField(columnName = "JobPositionID")
    private String JobPositionID;
    @DatabaseField(columnName = "Description")
    private String Description;
    @DatabaseField(columnName = "IsActive")
    private String IsActive;

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
}
