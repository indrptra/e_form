package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class ProductOfferingObjt {

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

    public ProductOfferingObjt(String description, String productOfferingId, String productId) {
        this.description = description;
        this.productId = productId;
        this.productOfferingId = productOfferingId;
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

    @Override
    public String toString() {
        return productOfferingId + " , " + description;
    }

}
