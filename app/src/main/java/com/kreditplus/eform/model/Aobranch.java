package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 15/03/17.
 */

@DatabaseTable(tableName = "Aobranch")
public class Aobranch {

    @DatabaseField(columnName = "BranchIDAo")
    private String branchIdAo;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    @DatabaseField(columnName = "firstSyncDate")
    private String firstSyncDate;

    @DatabaseField(columnName = "BranchName")
    private String branchName;

    public String getBranchIdAo() {
        return branchIdAo;
    }

    public void setBranchIdAo(String branchIdAo) {
        this.branchIdAo = branchIdAo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFirstSyncDate() {
        return firstSyncDate;
    }

    public void setFirstSyncDate(String firstSyncDate) {
        this.firstSyncDate = firstSyncDate;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
