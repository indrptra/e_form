package com.kreditplus.eform.model.ArrayList;

public class TipeKendaraanArrayList {
    private String AssetCode;
    private String Description;

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

    public TipeKendaraanArrayList(String assetCode, String description) {
        AssetCode = assetCode;
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }
}
