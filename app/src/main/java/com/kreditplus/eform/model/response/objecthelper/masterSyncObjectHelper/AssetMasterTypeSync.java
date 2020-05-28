package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetMasterTypeSync {

    @SerializedName("AssetCode")
    @Expose
    private String assetCode;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("AssetTypeId")
    @Expose
    private String assetTypeId;
    @SerializedName("DtmUpd")
    @Expose
    private String dtmUpd;

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

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getDtmUpd() {
        return dtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        this.dtmUpd = dtmUpd;
    }
}
