package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 16/02/17.
 */
@DatabaseTable(tableName = "IndustryType")
public class IndustryType {

    public  static final String IndustryTypeID = "IndustryTypeId";
    public  static final String Description2 = "Description";
    public  static final String IsActiveindustri = "IsActive";

    @DatabaseField(columnName = "IndustryTypeID")
    private int idIndustryType;

    @DatabaseField(columnName = "Description")
    private String Descrption;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    public int getIdIndustryType() {
        return idIndustryType;
    }

    public void setIdIndustryType(int idIndustryType) {
        this.idIndustryType = idIndustryType;
    }

    public String getDescrption() {
        return Descrption;
    }

    public void setDescrption(String descrption) {
        Descrption = descrption;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
