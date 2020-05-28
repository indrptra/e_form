package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

public class ProdOfSuppMapping {
    @SerializedName("BranchID")
    private String BranchID;
    @SerializedName("ProductID")
    private String ProductID;
    @SerializedName("ProductIDDescription")
    private String ProductIDDescription;
    @SerializedName("ProductOfferingID")
    private String ProductOfferingID;
    @SerializedName("ProductOfferingIDDescription")
    private String ProductOfferingIDDescription;
    @SerializedName("SupplierID")
    private String SupplierID;

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductIDDescription() {
        return ProductIDDescription;
    }

    public void setProductIDDescription(String productIDDescription) {
        ProductIDDescription = productIDDescription;
    }

    public String getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public String getProductOfferingIDDescription() {
        return ProductOfferingIDDescription;
    }

    public void setProductOfferingIDDescription(String productOfferingIDDescription) {
        ProductOfferingIDDescription = productOfferingIDDescription;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    @Override
    public String toString() {
        return ProductOfferingID + " , " + ProductOfferingIDDescription;
    }
}
