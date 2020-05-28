package com.kreditplus.eform.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetCategory {
    private String categoryID;
    private String description;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
