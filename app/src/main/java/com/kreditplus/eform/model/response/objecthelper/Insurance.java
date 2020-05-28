package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class Insurance {

    @SerializedName("CoverageType")
    @Expose
    public String coverageType;
    @SerializedName("IsPersonalAccident")
    @Expose
    public String isPersonalAccident;
    @SerializedName("IsPremiManual")
    @Expose
    public String isPremiManual;
    @SerializedName("PremiAsuransiAgunan")
    @Expose
    public String premiAsuransiAgunan;
    @SerializedName("PremiAsuransiJiwa")
    @Expose
    public String premiAsuransiJiwa;
    @SerializedName("PremiumAmountToCustomer")
    @Expose
    public String premiumAmountToCustomer;

    public String getPremiumAmountToCustomer() {
        return premiumAmountToCustomer;
    }

    public void setPremiumAmountToCustomer(String premiumAmountToCustomer) {
        this.premiumAmountToCustomer = premiumAmountToCustomer;
    }

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }

    public String getIsPersonalAccident() {
        return isPersonalAccident;
    }

    public void setIsPersonalAccident(String isPersonalAccident) {
        this.isPersonalAccident = isPersonalAccident;
    }

    public String getIsPremiManual() {
        return isPremiManual;
    }

    public void setIsPremiManual(String isPremiManual) {
        this.isPremiManual = isPremiManual;
    }

    public String getPremiAsuransiAgunan() {
        return premiAsuransiAgunan;
    }

    public void setPremiAsuransiAgunan(String premiAsuransiAgunan) {
        this.premiAsuransiAgunan = premiAsuransiAgunan;
    }

    public String getPremiAsuransiJiwa() {
        return premiAsuransiJiwa;
    }

    public void setPremiAsuransiJiwa(String premiAsuransiJiwa) {
        this.premiAsuransiJiwa = premiAsuransiJiwa;
    }
}
