package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tblNewIndustryTypeMaster")
public class TblNewIndustryTypeMaster {

    @DatabaseField(columnName = "IndustryTypeID")
    private String IndustryTypeID;

    @DatabaseField(columnName = "Description")
    private String Description;

    @DatabaseField(columnName = "IsActive")
    private String IsActive;

    public String getIndustryTypeID() {
        return IndustryTypeID;
    }

    public void setIndustryTypeID(String industryTypeID) {
        IndustryTypeID = industryTypeID;
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
