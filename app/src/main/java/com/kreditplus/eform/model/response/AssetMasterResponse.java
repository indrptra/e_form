package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.AssetMasterResponseList;

import java.util.List;

public class AssetMasterResponse {
    @SerializedName("AssetMaster")
    private List<AssetMasterResponseList> assetMasters;

    public List<AssetMasterResponseList> getAssetMasters() {
        return assetMasters;
    }

    public void setAssetMasters(List<AssetMasterResponseList> assetMasters) {
        this.assetMasters = assetMasters;
    }
}

