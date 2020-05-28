package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 16/02/17.
 */
@DatabaseTable(tableName = "TblProfessionJobType")
public class ProfessionJobType {

    public static final String ProfessionID = "ProfessionID";
    public static final String Description = "Description";
    public static final String JobTypeIdType = "JobTypeID";

    @DatabaseField(columnName = "ProfessionID")
    private String professionId;

    @DatabaseField(columnName = "JobTypeID")
    private String jobTypeId;

    @DatabaseField(columnName = "Description")
    private String description;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
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
