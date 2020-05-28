package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class SupplierMasterObjt {

    @SerializedName("BranchID")
    private String BranchID;

    @SerializedName("SupplierID")
    private String SupplierID;

    @SerializedName("SupplierName")
    private String SupplierName;

    @SerializedName("ContactPersonHP")
    private String ContactPersonHP;

    @SerializedName("ContactPersonEmail")
    private String ContactPersonEmail;

    @SerializedName("VarietyOfAssetSell")
    private String VarietyOfAssetSell;

    @SerializedName("SupplierAccID")
    private String SupplierAccID;

    @SerializedName("SupplierAccountNo")
    private String SupplierAccountNo;

    @SerializedName("SupplierAccountName")
    private String SupplierAccountName;

    @SerializedName("IsActive")
    private String IsActive;

    @SerializedName("DtmUpd")
    private String DtmUpd;

    @Override
    public String toString() {
        return SupplierID + " , " + SupplierName;
    }

    public SupplierMasterObjt(String supplierID, String supplierName) {
        SupplierID = supplierID;
        SupplierName = supplierName;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
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

    public String getVarietyOfAssetSell() {
        return VarietyOfAssetSell;
    }

    public void setVarietyOfAssetSell(String varietyOfAssetSell) {
        VarietyOfAssetSell = varietyOfAssetSell;
    }

    public String getSupplierAccID() {
        return SupplierAccID;
    }

    public void setSupplierAccID(String supplierAccID) {
        SupplierAccID = supplierAccID;
    }

    public String getSupplierAccountNo() {
        return SupplierAccountNo;
    }

    public void setSupplierAccountNo(String supplierAccountNo) {
        SupplierAccountNo = supplierAccountNo;
    }

    public String getSupplierAccountName() {
        return SupplierAccountName;
    }

    public void setSupplierAccountName(String supplierAccountName) {
        SupplierAccountName = supplierAccountName;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getDtmUpd() {
        return DtmUpd;
    }

    public void setDtmUpd(String dtmUpd) {
        DtmUpd = dtmUpd;
    }
}
