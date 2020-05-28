package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 30/03/17.
 */
@DatabaseTable(tableName = "resultAobranch")
public class ResultAobranch {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "Branchid")
    private String branchId;

    @DatabaseField(columnName = "ResultBranch")
    private String resultBranch;

    @DatabaseField(columnName = "isActive")
    private String isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getResultBranch() {
        return resultBranch;
    }

    public void setResultBranch(String resultBranch) {
        this.resultBranch = resultBranch;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
