package com.kreditplus.eform.model.ArrayList;

public class SupplierMasterArrayList {
    private String SupplierID;
    private String SupplierName;

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public SupplierMasterArrayList(String supplierID, String supplierName) {
        SupplierID = supplierID;
        SupplierName = supplierName;
    }

    @Override
    public String toString() {
        return SupplierName;
    }
}
