package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class DetailProduct {

    @SerializedName("PaymentMethod")
    @Expose
    public String paymentMethod;
    @SerializedName("PaymentMethodID")
    @Expose
    public String paymentMethodID;
    @SerializedName("ProductOfferingID")
    @Expose
    public String productOfferingID;
    @SerializedName("ProductID")
    @Expose
    public String productID;
    @SerializedName("ProductOfferingName")
    @Expose
    public String productOfferingName;
    @SerializedName("NumOfAssetUnit")
    @Expose
    public Integer numOfAssetUnit;
    @SerializedName("POSID")
    @Expose
    public String pOSID;
    @SerializedName("POSName")
    @Expose
    public String pOSName;
    @SerializedName("Tenor")
    @Expose
    public String tenor;
    @SerializedName("ProductName")
    @Expose
    public String productName;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public String getProductOfferingID() {
        return productOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        this.productOfferingID = productOfferingID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductOfferingName() {
        return productOfferingName;
    }

    public void setProductOfferingName(String productOfferingName) {
        this.productOfferingName = productOfferingName;
    }

    public Integer getNumOfAssetUnit() {
        return numOfAssetUnit;
    }

    public void setNumOfAssetUnit(Integer numOfAssetUnit) {
        this.numOfAssetUnit = numOfAssetUnit;
    }

    public String getpOSID() {
        return pOSID;
    }

    public void setpOSID(String pOSID) {
        this.pOSID = pOSID;
    }

    public String getpOSName() {
        return pOSName;
    }

    public void setpOSName(String pOSName) {
        this.pOSName = pOSName;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
