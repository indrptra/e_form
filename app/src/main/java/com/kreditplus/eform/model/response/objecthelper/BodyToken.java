package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apc-lap012 on 14/03/17.
 */

public class BodyToken {

    @SerializedName("type")
    private String type;

    @SerializedName("handphone")
    private String handphone;

    @SerializedName("mobile_submission_key")
    private String mobileSubmision;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("InstallmentAmmount")
    private String installmentAmount;

    @SerializedName("applicationPID")
    private String applicationPid;

    @SerializedName("Asset")
    private List<AssetToken> assetTokenList;

    @SerializedName("processType")
    private String processType;

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getMobileSubmision() {
        return mobileSubmision;
    }

    public void setMobileSubmision(String mobileSubmision) {
        this.mobileSubmision = mobileSubmision;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getApplicationPid() {
        return applicationPid;
    }

    public void setApplicationPid(String applicationPid) {
        this.applicationPid = applicationPid;
    }

    public List<AssetToken> getAssetTokenList() {
        return assetTokenList;
    }

    public void setAssetTokenList(List<AssetToken> assetTokenList) {
        this.assetTokenList = assetTokenList;
    }
}
