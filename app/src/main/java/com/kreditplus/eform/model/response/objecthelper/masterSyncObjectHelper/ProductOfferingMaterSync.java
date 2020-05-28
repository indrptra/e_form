package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class ProductOfferingMaterSync {

    @SerializedName("BranchID")
    private String branchId;

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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOfferingId() {
        return productOfferingId;
    }

    public void setProductOfferingId(String productOfferingId) {
        this.productOfferingId = productOfferingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(int assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getUsedNewType() {
        return usedNewType;
    }

    public void setUsedNewType(String usedNewType) {
        this.usedNewType = usedNewType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getInstallmentScheme() {
        return installmentScheme;
    }

    public void setInstallmentScheme(String installmentScheme) {
        this.installmentScheme = installmentScheme;
    }

    public String getAssetUsedNew() {
        return assetUsedNew;
    }

    public void setAssetUsedNew(String assetUsedNew) {
        this.assetUsedNew = assetUsedNew;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getPurposeofFinancingID() {
        return purposeofFinancingID;
    }

    public void setPurposeofFinancingID(String purposeofFinancingID) {
        this.purposeofFinancingID = purposeofFinancingID;
    }

    public String getWayofFinancingID() {
        return wayofFinancingID;
    }

    public void setWayofFinancingID(String wayofFinancingID) {
        this.wayofFinancingID = wayofFinancingID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
