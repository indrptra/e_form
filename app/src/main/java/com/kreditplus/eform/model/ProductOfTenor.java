package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apc-lap012 on 17/02/17.
 */
@DatabaseTable(tableName = "productoftenor")
public class ProductOfTenor {

    public static final String ProductID = "ProductId";
    public static final String ProductOfferingId2 = "ProductOfferingId";
    public static final String Tenor = "Tenor";
    public static final String EffectiveRate = "EffectiveRate";
    public static final String AdminFee = "AdminFee";
    public static final String FlatRate = "FlatRate";
    public static final String NTF = "NTF";
    public static final String IsActive = "IsActive";
    public static final String FirstInstallment ="FirstInstallment";
    public static final String DiscountRateTimes ="DiscountRateTimes";

    @DatabaseField(columnName = "BranchID")
    private String branchId;

    @DatabaseField(columnName = "ProductID")
    private String productId;

    @DatabaseField(columnName = "ProductOfferingId")
    private String productOfferingId;

    @DatabaseField(columnName = "Tenor")
    private String tenor;

    @DatabaseField(columnName = "EffectiveRate")
    private String effectiveRate;

    @DatabaseField(columnName = "GrossYieldRate")
    private String grossYieldRate;

    @DatabaseField(columnName = "AdminFee")
    private int adminFee;

    @DatabaseField(columnName = "WarningMinimumIncome")
    private int warningMinimumIncome;

    @DatabaseField(columnName = "FlatRate")
    private String flatRate;

    @DatabaseField(columnName = "NTF")
    private int ntf;

    @DatabaseField(columnName = "AdminFeeFlag")
    private String AdminFeeFlag;

    @DatabaseField(columnName = "AdminFeeForNTFPercentage")
    private int adminFeeForNTFPercentage;

    @DatabaseField(columnName = "FirstInstallment")
    private String firstInstallment;

    @DatabaseField(columnName = "DiscountOTRPercentage")
    private int discountOTRPercentage;

    @DatabaseField(columnName = "DiscountOTRAmount")
    private int discountOTRAmount;

    @DatabaseField(columnName = "DiscountInstallmentPercentage")
    private int discountInstallmentPercentage;

    @DatabaseField(columnName = "IsActive")
    private String isActive;

    @DatabaseField(columnName = "FiduciaFee")
    private int fiduciafee;

    @DatabaseField(columnName = "InstallmentAmount")
    private int installmentAmount;

    @DatabaseField(columnName = "DiscountRateTimes")
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

    public int getFiduciafee() {
        return fiduciafee;
    }

    public void setFiduciafee(int fiduciafee) {
        this.fiduciafee = fiduciafee;
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
