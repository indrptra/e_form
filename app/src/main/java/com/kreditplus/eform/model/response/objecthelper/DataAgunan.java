package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 13/10/16.
 */

public class DataAgunan {

    @SerializedName("MotorVehicle")
    private String motorVehicle;
    @SerializedName("Merk")
    private String merk;
    @SerializedName("Type")
    private String type;
    @SerializedName("Year")
    private String year;
    @SerializedName("Colour")
    private String colour;
    @SerializedName("CC")
    private String cc;
    @SerializedName("NoPolisi")
    private String noPolisi;
    @SerializedName("NoRangka")
    private String noRangka;
    @SerializedName("NoMesin")
    private String noMesin;
    @SerializedName("BPKBName")
    private String bpkbName;
    @SerializedName("CollateralValue")
    private String collateralValue;

    public String getMotorVehicle() {
        return motorVehicle;
    }

    public void setMotorVehicle(String motorVehicle) {
        this.motorVehicle = motorVehicle;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getNoPolisi() {
        return noPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }

    public String getNoRangka() {
        return noRangka;
    }

    public void setNoRangka(String noRangka) {
        this.noRangka = noRangka;
    }

    public String getNoMesin() {
        return noMesin;
    }

    public void setNoMesin(String noMesin) {
        this.noMesin = noMesin;
    }

    public String getBpkbName() {
        return bpkbName;
    }

    public void setBpkbName(String bpkbName) {
        this.bpkbName = bpkbName;
    }

    public String getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(String collateralValue) {
        this.collateralValue = collateralValue;
    }
}
