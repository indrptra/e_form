package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TblAssetMasterTypeKendaraan")
public class TblAssetMasterTypeKendaraan {

    @DatabaseField(columnName = "AssetCode")
    private String AssetCode;

    @DatabaseField(columnName = "Description")
    private String Description;

    @DatabaseField(columnName = "AssetTypeId")
    private String AssetTypeId;

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

    public String getAssetTypeId() {
        return AssetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        AssetTypeId = assetTypeId;
    }
}
