package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 17/02/17.
 */
@DatabaseTable(tableName = "productofferingmaster")
public class ProductOfferingMaster {

    public static final String Description = "Description";
    public static final String ProductID2 = "ProductID";
    public static final String ProductOfferingID = "ProductOfferingID";
    public static final String AssetTypeID = "AssetTypeID";
    public static final String SupplierID = "SupplierID";
    public static final String SupplierName = "SupplierName";

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "ProductID")
    private String productId;

    @DatabaseField(columnName = "ProductOfferingID")
    private String productOfferingId;

    @DatabaseField(columnName = "Description")
    private String description;

    @DatabaseField(columnName = "AssetTypeID")
    private int assetTypeId;

    @DatabaseField(columnName = "UsedNewType")
    private String usedNewType;

    @DatabaseField(columnName = "ProductType")
    private String productType;

    @DatabaseField(columnName = "FinanceType")
    private String financeType;

    @DatabaseField(columnName = "InterestType")
    private String interestType;

    @DatabaseField(columnName = "InstallmentScheme")
    private String installmentScheme;

    @DatabaseField(columnName = "AssetUsedNew")
    private String assetUsedNew;

    @DatabaseField(columnName = "RateType")
    private String rateType;

    @DatabaseField(columnName = "PurposeofFinancingID")
    private String purposeofFinancingID;

    @DatabaseField(columnName = "WayofFinancingID")
    private String wayofFinancingID;

    @DatabaseField(columnName = "StartDate")
    private String startDate;

    @DatabaseField(columnName = "EndDate")
    private String endDate;

    @DatabaseField(columnName = "SupplierID")
    private String supplierID;

    @DatabaseField(columnName = "SupplierName")
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

    public static String getProductID2() {
        return ProductID2;
    }

    public static String getProductOfferingID() {
        return ProductOfferingID;
    }

    public static String getAssetTypeID() {
        return AssetTypeID;
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
