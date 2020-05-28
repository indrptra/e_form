package com.kreditplus.eform.model.ArrayList;

public class NamaBarangArrayList {
    private String AssetCode;
    private String Description;
    private String CategoryID;

    public NamaBarangArrayList(String assetCode, String description, String categoryID) {
        AssetCode = assetCode;
        Description = description;
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

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    @Override
    public String toString() {
        return Description;
    }
}
