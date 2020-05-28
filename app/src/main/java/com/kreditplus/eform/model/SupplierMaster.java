package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 17/02/17.
 */
@DatabaseTable(tableName = "suppliermaster")
public class SupplierMaster {

    @DatabaseField(columnName = "BranchID")
    private String BranchID;

    @DatabaseField(columnName = "SupplierID")
    private String supplierID;

    @DatabaseField(columnName = "SupplierName")
    private String supplierName;

    @DatabaseField(columnName = "VarietyOfAssetSell")
    private String varietyOfAssetSell;

    @DatabaseField(columnName = "SupplierAccID")
    private int supplierAccID;

    @DatabaseField(columnName = "SupplierAccountNo")
    private String supplierAccountNo;

    @DatabaseField(columnName = "SupplierAccountName")
    private String supplierAccountName;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    @DatabaseField(columnName = "DtmUpd")
    private String DtmUpd;

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getVarietyOfAssetSell() {
        return varietyOfAssetSell;
    }

    public void setVarietyOfAssetSell(String varietyOfAssetSell) {
        this.varietyOfAssetSell = varietyOfAssetSell;
    }

    public int getSupplierAccID() {
        return supplierAccID;
    }

    public void setSupplierAccID(int supplierAccID) {
        this.supplierAccID = supplierAccID;
    }

    public String getSupplierAccountNo() {
        return supplierAccountNo;
    }

    public void setSupplierAccountNo(String supplierAccountNo) {
        this.supplierAccountNo = supplierAccountNo;
    }

    public String getSupplierAccountName() {
        return supplierAccountName;
    }

    public void setSupplierAccountName(String supplierAccountName) {
        this.supplierAccountName = supplierAccountName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDtmUpd() {
        return DtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        DtmUpd = dtmUpd;
    }
}
