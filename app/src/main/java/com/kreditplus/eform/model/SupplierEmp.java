package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 17/02/17.
 */
@DatabaseTable(tableName = "supplieremp")
public class SupplierEmp {

    public static final String SupplierID3 = "SupplierID";
    public static final String SupplierEmployeeID = "supplierEmployeeID";
    public static final String SupplierEmployeeName = "supplierEmployeeName";

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "SupplierID")
    private String supplierID;

    @DatabaseField(columnName = "SupplierEmployeeID")
    private String supplierEmployeeID;

    @DatabaseField(columnName = "SupplierEmployeeName")
    private String supplierEmployeeName;

    @DatabaseField(columnName = "SupplierEmployeePosition")
    private String supplierEmployeePosition;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierEmployeeID() {
        return supplierEmployeeID;
    }

    public void setSupplierEmployeeID(String supplierEmployeeID) {
        this.supplierEmployeeID = supplierEmployeeID;
    }

    public String getSupplierEmployeeName() {
        return supplierEmployeeName;
    }

    public void setSupplierEmployeeName(String supplierEmployeeName) {
        this.supplierEmployeeName = supplierEmployeeName;
    }

    public String getSupplierEmployeePosition() {
        return supplierEmployeePosition;
    }

    public void setSupplierEmployeePosition(String supplierEmployeePosition) {
        this.supplierEmployeePosition = supplierEmployeePosition;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
