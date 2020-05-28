package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class SupplierMasterSync {

    @SerializedName("BranchID")
    private String branchId;

    @SerializedName("SupplierID")
    private String supplierID;

    @SerializedName("SupplierName")
    private String supplierName;

    @SerializedName("ContactPersonHP")
    private String ContactPersonHP;

    @SerializedName("ContactPersonEmail")
    private String ContactPersonEmail;

    @SerializedName("VarietyOfAssetSell")
    private String varietyOfAssetSell;

    @SerializedName("SupplierAccID")
    private String supplierAccID;

    @SerializedName("SupplierAccountNo")
    private String supplierAccountNo;

    @SerializedName("SupplierAccountName")
    private String supplierAccountName;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("DtmUpd")
    private String DtmUpd;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public String getSupplierAccID() {
        return supplierAccID;
    }

    public void setSupplierAccID(String supplierAccID) {
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

    public String getContactPersonHP() {
        return ContactPersonHP;
    }

    public void setContactPersonHP(String contactPersonHP) {
        ContactPersonHP = contactPersonHP;
    }

    public String getContactPersonEmail() {
        return ContactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        ContactPersonEmail = contactPersonEmail;
    }
}
