package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 22/03/17.
 */

public class KorpFormulir {
    @SerializedName("SalesMethod")
    @Expose
    public String salesMethod;
    @SerializedName("CreditStatus")
    @Expose
    public String creditStatus;
    @SerializedName("FinancingPurpose")
    @Expose
    public String financingPurpose;
    @SerializedName("TypeForm")
    @Expose
    public String typeForm;
    @SerializedName("TypeOfFinancing")
    @Expose
    public String typeOfFinancing;
    @SerializedName("TypeOfFinancingID")
    @Expose
    public String typeOfFinancingID;
    @SerializedName("PurposeOfUseFunds")
    @Expose
    public String purposeOfUseFunds;
    @SerializedName("PurposeOfUseFundsID")
    @Expose
    public String purposeOfUseFundsID;
    @SerializedName("SalesMethodID")
    @Expose
    public String salesMethodID;

    public String getTypeForm() {
        return typeForm;
    }

    public void setTypeForm(String typeForm) {
        this.typeForm = typeForm;
    }

    public String getSalesMethod() {
        return salesMethod;
    }

    public void setSalesMethod(String salesMethod) {
        this.salesMethod = salesMethod;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getFinancingPurpose() {
        return financingPurpose;
    }

    public void setFinancingPurpose(String financingPurpose) {
        this.financingPurpose = financingPurpose;
    }

    public String getTypeOfFinancing() {
        return typeOfFinancing;
    }

    public void setTypeOfFinancing(String typeOfFinancing) {
        this.typeOfFinancing = typeOfFinancing;
    }

    public String getTypeOfFinancingID() {
        return typeOfFinancingID;
    }

    public void setTypeOfFinancingID(String typeOfFinancingID) {
        this.typeOfFinancingID = typeOfFinancingID;
    }

    public String getPurposeOfUseFunds() {
        return purposeOfUseFunds;
    }

    public void setPurposeOfUseFunds(String purposeOfUseFunds) {
        this.purposeOfUseFunds = purposeOfUseFunds;
    }

    public String getPurposeOfUseFundsID() {
        return purposeOfUseFundsID;
    }

    public void setPurposeOfUseFundsID(String purposeOfUseFundsID) {
        this.purposeOfUseFundsID = purposeOfUseFundsID;
    }

    public String getSalesMethodID() {
        return salesMethodID;
    }

    public void setSalesMethodID(String salesMethodID) {
        this.salesMethodID = salesMethodID;
    }
}
