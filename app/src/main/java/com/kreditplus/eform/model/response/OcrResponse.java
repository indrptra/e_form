package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ignatius on 10/26/2017.
 */

public class OcrResponse {

    @SerializedName("Gender")
    private String genderOcr;

    @SerializedName("FullName")
    private String fullNameOcr;

    @SerializedName("Agama")
    private String agamaOcr;

    @SerializedName("KTPNumber")
    private String ktpNumberOcr;

    @SerializedName("BirthDate")
    private String birtdateOcr;

    public String getKtpNumberOcr() {
        return ktpNumberOcr;
    }

    public void setKtpNumberOcr(String ktpNumberOcr) {
        this.ktpNumberOcr = ktpNumberOcr;
    }

    public String getBirtdateOcr() {
        return birtdateOcr;
    }

    public void setBirtdateOcr(String birtdateOcr) {
        this.birtdateOcr = birtdateOcr;
    }

    public String getGenderOcr() {
        return genderOcr;
    }

    public void setGenderOcr(String genderOcr) {
        this.genderOcr = genderOcr;
    }

    public String getFullNameOcr() {
        return fullNameOcr;
    }

    public void setFullNameOcr(String fullNameOcr) {
        this.fullNameOcr = fullNameOcr;
    }

    public String getAgamaOcr() {
        return agamaOcr;
    }

    public void setAgamaOcr(String agamaOcr) {
        this.agamaOcr = agamaOcr;
    }
}
