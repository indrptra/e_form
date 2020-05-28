package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class ProductOfTenorSync {

    @SerializedName("BranchID")
    private String branchId;

    @SerializedName("ProductId")
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
    private int adminFee;

    @SerializedName("WarningMinimumIncome")
    private int warningMinimumIncome;

    @SerializedName("FlatRate")
    private String flatRate;

    @SerializedName("NTF")
    private int ntf;

    @SerializedName("AdminFeeFlag")
    private String AdminFeeFlag;

    @SerializedName("AdminFeeForNTFPercentage")
    private int adminFeeForNTFPercentage;

    @SerializedName("FirstInstallment")
    private String firstInstallment;

    @SerializedName("DiscountOTRPercentage")
    private int discountOTRPercentage;

    @SerializedName("DiscountOTRAmount")
    private int discountOTRAmount;

    @SerializedName("DiscountInstallmentPercentage")
    private int discountInstallmentPercentage;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("FidusiaFee")
    private int fidusiaFee;

    @SerializedName("InstallmentAmount")
    private int installmentAmount;

    @SerializedName("DiscountRateTimes")
    private int discountRateTimes;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public int getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(int adminFee) {
        this.adminFee = adminFee;
    }

    public int getWarningMinimumIncome() {
        return warningMinimumIncome;
    }

    public void setWarningMinimumIncome(int warningMinimumIncome) {
        this.warningMinimumIncome = warningMinimumIncome;
    }

    public String getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(String flatRate) {
        this.flatRate = flatRate;
    }

    public int getNtf() {
        return ntf;
    }

    public void setNtf(int ntf) {
        this.ntf = ntf;
    }

    public String getAdminFeeFlag() {
        return AdminFeeFlag;
    }

    public void setAdminFeeFlag(String adminFeeFlag) {
        AdminFeeFlag = adminFeeFlag;
    }

    public int getAdminFeeForNTFPercentage() {
        return adminFeeForNTFPercentage;
    }

    public void setAdminFeeForNTFPercentage(int adminFeeForNTFPercentage) {
        this.adminFeeForNTFPercentage = adminFeeForNTFPercentage;
    }

    public String getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public int getDiscountOTRPercentage() {
        return discountOTRPercentage;
    }

    public void setDiscountOTRPercentage(int discountOTRPercentage) {
        this.discountOTRPercentage = discountOTRPercentage;
    }

    public int getDiscountOTRAmount() {
        return discountOTRAmount;
    }

    public void setDiscountOTRAmount(int discountOTRAmount) {
        this.discountOTRAmount = discountOTRAmount;
    }

    public int getDiscountInstallmentPercentage() {
        return discountInstallmentPercentage;
    }

    public void setDiscountInstallmentPercentage(int discountInstallmentPercentage) {
        this.discountInstallmentPercentage = discountInstallmentPercentage;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getFidusiaFee() {
        return fidusiaFee;
    }

    public void setFidusiaFee(int fidusiaFee) {
        this.fidusiaFee = fidusiaFee;
    }

    public int getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(int installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public int getDiscountRateTimes() {
        return discountRateTimes;
    }

    public void setDiscountRateTimes(int discountRateTimes) {
        this.discountRateTimes = discountRateTimes;
    }
}
