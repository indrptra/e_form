package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 17/02/17.
 */
@DatabaseTable(tableName = "posmaster")
public class PosMaster {

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "POSID")
    private String posId;

    @DatabaseField(columnName = "POSName")
    private String posName;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }
}
