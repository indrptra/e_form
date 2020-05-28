package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class AssetMasterObjt {

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

    public AssetMasterObjt(String description, String assetCode, String categoryId){
        this.description = description;
        this.assetCode = assetCode;
        this.categoryId = categoryId;

    }
    public String getAssetCode() {
        return assetCode;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString(){
        return description;
    }
}
