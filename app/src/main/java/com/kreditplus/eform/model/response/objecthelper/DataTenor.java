package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 02/02/17.
 */

public class DataTenor {
    @SerializedName("Tenors")
    private String tenors;
    @SerializedName("NTF")
    private String ntf;
    @SerializedName("EffectiveRate")
    private String effectiveRate;
    @SerializedName("AdminFee")
    private String adminFee;
    @SerializedName("FlatRate")
    private String flatRate;

    public String getTenors() {
        return tenors;
    }

    public void setTenors(String tenors) {
        this.tenors = tenors;
    }

    public String getNtf() {
        return ntf;
    }

    public void setNtf(String ntf) {
        this.ntf = ntf;
    }

    public String getEffectiveRate() {
        return effectiveRate;
    }

    public void setEffectiveRate(String effectiveRate) {
        this.effectiveRate = effectiveRate;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(String flatRate) {
        this.flatRate = flatRate;
    }
}
