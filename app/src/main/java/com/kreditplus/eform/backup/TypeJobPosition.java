package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 16/02/17.
 */
@DatabaseTable(tableName = "tblJobTypeJobPosition")
public class TypeJobPosition {

    public static final String JobTypeID = "JobTypeID";
    public static final String JobPositionId2 = "JobPositionID";
    public static final String DescriptionPos = "Description";

    @DatabaseField(columnName = "JobTypeID")
    private String jobTypeId;

    @DatabaseField(columnName = "JobPositionID")
    private String jobPositionId;

    @DatabaseField(columnName = "Description")
    private String description;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(String jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
