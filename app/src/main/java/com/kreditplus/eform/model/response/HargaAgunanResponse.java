package com.kreditplus.eform.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HargaAgunanResponse extends BaseResponse{
    @SerializedName("BranchID")
    @Expose
    private String branchID;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("AssetCode")
    @Expose
    private String assetCode;
    @SerializedName("ManufacturingYear")
    @Expose
    private String manufacturingYear;
    @SerializedName("MarketPriceValue")
    @Expose
    private String marketPriceValue;
    @SerializedName("DtmUpd")
    @Expose
    private String dtmUpd;

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
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

    public String getDtmUpd() {
        return dtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        this.dtmUpd = dtmUpd;
    }
}
