package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 16/02/17.
 */
@DatabaseTable(tableName = "AssetMaster")
public class AssetMaster {

    public static final String AssetTypeId2 ="assetTypeId";

    @DatabaseField(columnName = "AssetCode")
    private String assetCode;

    @DatabaseField(columnName = "Description")
    private String description;

    @DatabaseField(columnName = "CategoryID")
    private String categoryId;

    @DatabaseField(columnName = "AssetTypeId")
    private String assetTypeId;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    @DatabaseField(columnName = "DtmUpd")
    private String DtmUpd;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getDtmUpd() {
        return DtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        DtmUpd = dtmUpd;
    }
}
