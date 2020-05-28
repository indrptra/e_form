package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 16/03/17.
 */

public class MarketPriceMasterSync {

    @SerializedName("AssetCode")
    private String assetCode;

    @SerializedName("BranchID")
    private String branchId;

    @SerializedName("Brand")
    private String brand;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("ManufacturingYear")
    private String manufacturingYear;

    @SerializedName("MarketPriceValue")
    private String marketPriceValue;

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(String manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getMarketPriceValue() {
        return marketPriceValue;
    }

    public void setMarketPriceValue(String marketPriceValue) {
        this.marketPriceValue = marketPriceValue;
    }
}
