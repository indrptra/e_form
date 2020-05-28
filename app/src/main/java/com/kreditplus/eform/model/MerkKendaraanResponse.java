package com.kreditplus.eform.model;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetCategoryMasterSyncList;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.assetMasterFilter;

import java.util.List;

public class MerkKendaraanResponse {
    @SerializedName("assetMasterFilter")
    private List<assetMasterFilter> assetMasterFilterList;

    public List<assetMasterFilter> getAssetMasterFilterList() {
        return assetMasterFilterList;
    }

    public void setAssetMasterFilterList(List<assetMasterFilter> assetMasterFilterList) {
        this.assetMasterFilterList = assetMasterFilterList;
    }
}
