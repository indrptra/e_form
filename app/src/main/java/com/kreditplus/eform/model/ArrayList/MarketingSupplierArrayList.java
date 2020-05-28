package com.kreditplus.eform.model.ArrayList;

public class MarketingSupplierArrayList {
    private String SupplierEmployeeID;
    private String SupplierEmployeeName;

    public String getSupplierEmployeeID() {
        return SupplierEmployeeID;
    }

    public void setSupplierEmployeeID(String supplierEmployeeID) {
        SupplierEmployeeID = supplierEmployeeID;
    }

    public String getSupplierEmployeeName() {
        return SupplierEmployeeName;
    }

    public void setSupplierEmployeeName(String supplierEmployeeName) {
        SupplierEmployeeName = supplierEmployeeName;
    }

    public MarketingSupplierArrayList(String supplierEmployeeID, String supplierEmployeeName) {
        SupplierEmployeeID = supplierEmployeeID;
        SupplierEmployeeName = supplierEmployeeName;
    }

    @Override
    public String toString() {
        return SupplierEmployeeName;
    }
}
