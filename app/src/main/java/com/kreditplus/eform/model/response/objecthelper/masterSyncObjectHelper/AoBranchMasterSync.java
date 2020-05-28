package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 16/03/17.
 */

public class AoBranchMasterSync {

    @SerializedName("BranchID")
    private String branchId;

    @SerializedName("IsActive")
    private String isActive;

    @SerializedName("BranchFullName")
    private String branchFullName;

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

    public String getBranchFullName() {
        return branchFullName;
    }

    public void setBranchFullName(String branchFullName) {
        this.branchFullName = branchFullName;
    }
}
