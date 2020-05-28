package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class MarketingSupplierObjt {

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

    public MarketingSupplierObjt(String supplierID, String supplierEmployeeID, String supplierEmployeeName){
        this.supplierID = supplierID;
        this.supplierEmployeeID = supplierEmployeeID;
        this.supplierEmployeeName = supplierEmployeeName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierEmployeeID() {
        return supplierEmployeeID;
    }

    public String getSupplierEmployeeName() {
        return supplierEmployeeName;
    }

    public String getSupplierEmployeePosition() {
        return supplierEmployeePosition;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return supplierEmployeeName;
    }
}
