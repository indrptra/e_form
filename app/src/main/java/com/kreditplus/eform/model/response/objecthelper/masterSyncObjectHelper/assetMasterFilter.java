package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

public class assetMasterFilter {
    @SerializedName("AssetCode")
    private String AssetCode;

    @SerializedName("Description")
    private String Description;

    @SerializedName("AssetTypeId")
    private String AssetTypeId;

    @SerializedName("Merk")
    private String Merk;

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

    public String getAssetTypeId() {
        return AssetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        AssetTypeId = assetTypeId;
    }

    public String getMerk() {
        return Merk;
    }

    public void setMerk(String merk) {
        Merk = merk;
    }
}
