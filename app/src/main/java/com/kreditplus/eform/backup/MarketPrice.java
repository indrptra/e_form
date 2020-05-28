package com.kreditplus.eform.backup;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 15/03/17.
 */
@DatabaseTable(tableName = "MarketPlace")
public class MarketPrice {

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "Brand")
    private String brand;

    @DatabaseField(columnName = "AssetCode")
    private String assetCode;

    @DatabaseField(columnName = "ManufacturingYear")
    private String manufacturingYear;

    @DatabaseField(columnName = "MarketPriceValue")
    private String marketPriceValue;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

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

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
