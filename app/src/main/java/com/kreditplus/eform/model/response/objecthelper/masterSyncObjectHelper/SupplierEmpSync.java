package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class SupplierEmpSync {

    @SerializedName("BranchID")
    private String branchId;

    @SerializedName("SupplierID")
    private String supplierID;

    @SerializedName("SupplierEmployeeID")
    private String supplierEmployeeID;

    @SerializedName("SupplierEmployeeName")
    private String supplierEmployeeName;

    @SerializedName("SupplierEmployeePosition")
    private String supplierEmployeePosition;

    @SerializedName("IsActive")
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
