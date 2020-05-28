package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 14/03/17.
 */

public class AssetToken {

    @SerializedName("AssetCode")
    private String assetKode;

    @SerializedName("Type")
    private String type;

    public String getAssetKode() {
        return assetKode;
    }

    public void setAssetKode(String assetKode) {
        this.assetKode = assetKode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
