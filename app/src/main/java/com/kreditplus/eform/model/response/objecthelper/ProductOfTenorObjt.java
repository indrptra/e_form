package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 20/02/17.
 */

public class ProductOfTenorObjt {

    @SerializedName("ProductID")
    private String productId;

    @SerializedName("ProductOfferingId")
    private String productOfferingId;

    @SerializedName("Tenor")
    private String tenor;

    @SerializedName("EffectiveRate")
    private String effectiveRate;

    @SerializedName("GrossYieldRate")
    private String grossYieldRate;

    @SerializedName("AdminFee")
    private String adminFee;

    @SerializedName("WarningMinimumIncome")
    private String warningMinimumIncome;

    @SerializedName("FlatRate")
    private String flatRate;

    @SerializedName("NTF")
    private String ntf;

    @SerializedName("AdminFeeFlag")
    private String AdminFeeFlag;

    @SerializedName("AdminFeeForNTFPercentage")
    private String adminFeeForNTFPercentage;

    @SerializedName("FirstInstallment")
    private String firstInstallment;

    @SerializedName("DiscountOTRPercentage")
    private String discountOTRPercentage;

    @SerializedName("DiscountOTRAmount")
    private String discountOTRAmount;

    @SerializedName("DiscountInstallmentPercentage")
    private String discountInstallmentPercentage;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("FiduciaFee")
    private String fiduciafee;

    @SerializedName("InstallmentAmount")
    private String installmentAmount;

    @SerializedName("DPPercentage")
    private String dPPercentage;

    @SerializedName("DiscountRateTimes")
    private String discountRateTimes;

    public ProductOfTenorObjt(String tenor, String ntf, String flatRate, String effectiveRate, String adminFee, String firstInstallment,String discountRateTimes) {
        this.tenor = tenor;
        this.ntf = ntf;
        this.flatRate = flatRate;
        this.effectiveRate = effectiveRate;
        this.adminFee = adminFee;
        this.firstInstallment = firstInstallment;
        this.discountRateTimes = discountRateTimes;
    }

    public String getdPPercentage() {
        return dPPercentage;
    }

    public void setdPPercentage(String dPPercentage) {
        this.dPPercentage = dPPercentage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOfferingId() {
        return productOfferingId;
    }

    public void setProductOfferingId(String productOfferingId) {
        this.productOfferingId = productOfferingId;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getEffectiveRate() {
        return effectiveRate;
    }

    public void setEffectiveRate(String effectiveRate) {
        this.effectiveRate = effectiveRate;
    }

    public String getGrossYieldRate() {
        return grossYieldRate;
    }

    public void setGrossYieldRate(String grossYieldRate) {
        this.grossYieldRate = grossYieldRate;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getWarningMinimumIncome() {
        return warningMinimumIncome;
    }

    public void setWarningMinimumIncome(String warningMinimumIncome) {
        this.warningMinimumIncome = warningMinimumIncome;
    }

    public String getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(String flatRate) {
        this.flatRate = flatRate;
    }

    public String getNtf() {
        return ntf;
    }

    public void setNtf(String ntf) {
        this.ntf = ntf;
    }

    public String getAdminFeeFlag() {
        return AdminFeeFlag;
    }

    public void setAdminFeeFlag(String adminFeeFlag) {
        AdminFeeFlag = adminFeeFlag;
    }

    public String getAdminFeeForNTFPercentage() {
        return adminFeeForNTFPercentage;
    }

    public void setAdminFeeForNTFPercentage(String adminFeeForNTFPercentage) {
        this.adminFeeForNTFPercentage = adminFeeForNTFPercentage;
    }

    public String getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public String getDiscountOTRPercentage() {
        return discountOTRPercentage;
    }

    public void setDiscountOTRPercentage(String discountOTRPercentage) {
        this.discountOTRPercentage = discountOTRPercentage;
    }

    public String getDiscountOTRAmount() {
        return discountOTRAmount;
    }

    public void setDiscountOTRAmount(String discountOTRAmount) {
        this.discountOTRAmount = discountOTRAmount;
    }

    public String getDiscountInstallmentPercentage() {
        return discountInstallmentPercentage;
    }

    public void setDiscountInstallmentPercentage(String discountInstallmentPercentage) {
        this.discountInstallmentPercentage = discountInstallmentPercentage;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFiduciafee() {
        return fiduciafee;
    }

    public void setFiduciafee(String fiduciafee) {
        this.fiduciafee = fiduciafee;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getDiscountRateTimes() {
        return discountRateTimes;
    }

    public void setDiscountRateTimes(String discountRateTimes) {
        this.discountRateTimes = discountRateTimes;
    }

    @Override
    public String toString() {
        return tenor;
    }
}
