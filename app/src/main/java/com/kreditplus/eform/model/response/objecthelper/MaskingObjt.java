package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ignatius on 10/30/2017.
 */

public class MaskingObjt {

    @SerializedName("masking_tenor")
    private int tenor;
    @SerializedName("masking_flat_rate")
    private double returnRate;
    @SerializedName("masking_first_installment")
    private String fsInstallment;

    @Override
    public String toString() {
        return String.valueOf(tenor);
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
    }

    public String getFsInstallment() {
        return fsInstallment;
    }

    public void setFsInstallment(String fsInstallment) {
        this.fsInstallment = fsInstallment;
    }
}
