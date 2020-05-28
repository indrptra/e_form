package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 15/03/17.
 */

@DatabaseTable(tableName = "BranchMaster")
public class BranchMaster {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "BranchPrimary")
    private String branchPrimary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchPrimary() {
        return branchPrimary;
    }

    public void setBranchPrimary(String branchPrimary) {
        this.branchPrimary = branchPrimary;
    }
}
