package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

public class SupplierOfferingObjt {

    @SerializedName("ProductID")
    private String productId;

    @SerializedName("ProductOfferingID")
    private String productOfferingId;

    @SerializedName("Description")
    private String description;

    @SerializedName("AssetTypeID")
    private int assetTypeId;

    @SerializedName("UsedNewType")
    private String usedNewType;

    @SerializedName("ProductType")
    private String productType;

    @SerializedName("FinanceType")
    private String financeType;

    @SerializedName("InterestType")
    private String interestType;

    @SerializedName("InstallmentScheme")
    private String installmentScheme;

    @SerializedName("AssetUsedNew")
    private String assetUsedNew;

    @SerializedName("RateType")
    private String rateType;

    @SerializedName("PurposeofFinancingID")
    private String purposeofFinancingID;

    @SerializedName("WayofFinancingID")
    private String wayofFinancingID;

    @SerializedName("StartDate")
    private String startDate;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("SupplierID")
    private String supplierID;

    @SerializedName("SupplierName")
    private String supplierName;

    public SupplierOfferingObjt(String supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return supplierName;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductOfferingId() {
        return productOfferingId;
    }

    public String getDescription() {
        return description;
    }

    public int getAssetTypeId() {
        return assetTypeId;
    }

    public String getUsedNewType() {
        return usedNewType;
    }

    public String getProductType() {
        return productType;
    }

    public String getFinanceType() {
        return financeType;
    }

    public String getInterestType() {
        return interestType;
    }

    public String getInstallmentScheme() {
        return installmentScheme;
    }

    public String getAssetUsedNew() {
        return assetUsedNew;
    }

    public String getRateType() {
        return rateType;
    }

    public String getPurposeofFinancingID() {
        return purposeofFinancingID;
    }

    public String getWayofFinancingID() {
        return wayofFinancingID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
