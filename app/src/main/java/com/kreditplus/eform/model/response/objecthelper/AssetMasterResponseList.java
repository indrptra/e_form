package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

public class AssetMasterResponseList {
    @SerializedName("AssetCode")
    private String AssetCode;
    @SerializedName("Description")
    private String Description;
    @SerializedName("CategoryID")
    private String CategoryID;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }
}

