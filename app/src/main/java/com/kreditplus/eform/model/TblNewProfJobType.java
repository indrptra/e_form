package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tblNewProfJobType")
public class TblNewProfJobType {

    @DatabaseField(columnName = "ProfessionID")
    private String ProfessionID;
    @DatabaseField(columnName = "JobTypeID")
    private String JobTypeID;
    @DatabaseField(columnName = "Description")
    private String Description;
    @DatabaseField(columnName = "IsActive")
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
}
