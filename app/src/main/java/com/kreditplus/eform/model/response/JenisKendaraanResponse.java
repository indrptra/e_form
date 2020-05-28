package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AssetCategoryMasterSyncList;

import java.util.List;

public class JenisKendaraanResponse {
    @SerializedName("AssetCategory")
    private List<AssetCategoryMasterSyncList> assetCategoryMasterSyncList;

    public List<AssetCategoryMasterSyncList> getAssetCategoryMasterSyncList() {
        return assetCategoryMasterSyncList;
    }

    public void setAssetCategoryMasterSyncList(List<AssetCategoryMasterSyncList> assetCategoryMasterSyncList) {
        this.assetCategoryMasterSyncList = assetCategoryMasterSyncList;
    }
}
