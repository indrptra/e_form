package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ProductOfferingSupplier")
public class ProductOfferingSupplier {

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "ProductID")
    private String productID;

    @DatabaseField(columnName = "ProductOfferingID")
    private String productOfferingID;

    @DatabaseField(columnName = "SupplierID")
    private String supplierID;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductOfferingID() {
        return productOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        this.productOfferingID = productOfferingID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
}
