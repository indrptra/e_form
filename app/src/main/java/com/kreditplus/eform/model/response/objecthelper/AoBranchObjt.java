package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 16/03/17.
 */

public class AoBranchObjt {

    @SerializedName("Branchid")
    private String branchId;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("ResultBranch")
    private String branchName;

    public AoBranchObjt(String branchId, String isActive, String branchName) {
        this.branchId = branchId;
        this.isActive = isActive;
        this.branchName = branchName;
    }



    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return branchName;
    }
}
