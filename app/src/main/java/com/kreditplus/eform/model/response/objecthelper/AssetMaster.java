package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class AssetMaster {

    @SerializedName("SupplierID")
    @Expose
    public String supplierID;
    @SerializedName("SupplierName")
    @Expose
    public String supplierName;
    @SerializedName("SupplierBankAccountID")
    @Expose
    public String supplierBankAccountID;
    @SerializedName("SupplierBankAccountName")
    @Expose
    public String supplierBankAccountName;
    @SerializedName("SalesmanName")
    @Expose
    public String salesmanName;
    @SerializedName("SalesmanID")
    @Expose
    public String salesmanID;

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

    public String getSupplierBankAccountID() {
        return supplierBankAccountID;
    }

    public void setSupplierBankAccountID(String supplierBankAccountID) {
        this.supplierBankAccountID = supplierBankAccountID;
    }

    public String getSupplierBankAccountName() {
        return supplierBankAccountName;
    }

    public void setSupplierBankAccountName(String supplierBankAccountName) {
        this.supplierBankAccountName = supplierBankAccountName;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }
}
