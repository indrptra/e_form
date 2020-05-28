package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class AssetMasterSync {

    @SerializedName("AssetCode")
    private String assetCode;

    @SerializedName("AssetTypeId")
    private String assetTypeId;

    @SerializedName("Description")
    private String description;

    @SerializedName("CategoryID")
    private String categoryId;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("DtmUpd")
    private String DtmUpd;

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getDtmUpd() {
        return DtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        DtmUpd = dtmUpd;
    }
}
