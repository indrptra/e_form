package com.kreditplus.eform.model.ArrayList;

public class TenorArrayList {
    private String Tenor;
    private String AdminFee;
    private String Ntf;
    private String EffectiveRate;
    private String DPPercentage;

    public TenorArrayList(String tenor, String adminFee, String ntf, String effectiveRate, String DPPercentage) {
        Tenor = tenor;
        AdminFee = adminFee;
        Ntf = ntf;
        EffectiveRate = effectiveRate;
        this.DPPercentage = DPPercentage;
    }

    public String getDPPercentage() {
        return DPPercentage;
    }

    public void setDPPercentage(String DPPercentage) {
        this.DPPercentage = DPPercentage;
    }

    public String getEffectiveRate() {
        return EffectiveRate;
    }

    public void setEffectiveRate(String effectiveRate) {
        EffectiveRate = effectiveRate;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }

    public String getAdminFee() {
        return AdminFee;
    }

    public void setAdminFee(String adminFee) {
        AdminFee = adminFee;
    }

    public String getNtf() {
        return Ntf;
    }

    public void setNtf(String ntf) {
        Ntf = ntf;
    }

    @Override
    public String toString() {
        return Tenor;
    }
}
