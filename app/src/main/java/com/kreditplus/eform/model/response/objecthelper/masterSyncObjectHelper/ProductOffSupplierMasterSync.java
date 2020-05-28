package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

public class ProductOffSupplierMasterSync {

    @SerializedName("BranchID")
    private String branchId;
    @SerializedName("ProductID")
    private String productID;
    @SerializedName("ProductOfferingID")
    private String productOfferingID;
    @SerializedName("SupplierID")
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
