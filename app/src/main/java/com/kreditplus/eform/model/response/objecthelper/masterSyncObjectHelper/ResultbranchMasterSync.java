package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 30/03/17.
 */

public class ResultbranchMasterSync {

    @SerializedName("id")
    private int id;

    @SerializedName("Branchid")
    private  String branchId;

    @SerializedName("ResultBranch")
    private String resultBranch;

    @SerializedName("isActive")
    private String isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getResultBranch() {
        return resultBranch;
    }

    public void setResultBranch(String resultBranch) {
        this.resultBranch = resultBranch;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
