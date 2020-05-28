package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetCategoryMasterSyncList {
    @SerializedName("CategoryID")
    @Expose
    private String categoryID;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DtmUpd")
    @Expose
    private String dtmUpd;

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

    public String getDtmUpd() {
        return dtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        this.dtmUpd = dtmUpd;
    }
}
