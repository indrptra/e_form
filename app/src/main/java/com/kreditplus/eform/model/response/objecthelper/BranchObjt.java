package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 16/03/17.
 */

public class BranchObjt {

    @SerializedName("branchIdLintasCabang")
    private String aoBranch;
    @SerializedName("branchIdPrimary")
    private String masterBranch;
    @SerializedName("branchCodeName")
    private String branchCodeName;
    @SerializedName("Region")
    private String Region;

    public String getBranchCodeName() {
        return branchCodeName;
    }

    public void setBranchCodeName(String branchCodeName) {
        this.branchCodeName = branchCodeName;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getAoBranch() {
        return aoBranch;
    }

    public void setAoBranch(String aoBranch) {
        this.aoBranch = aoBranch;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }
}
