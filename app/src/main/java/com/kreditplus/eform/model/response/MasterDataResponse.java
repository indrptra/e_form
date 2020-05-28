package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.AoBranchMasterSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.DataFinansialSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.IndustryTypeSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.KelurahanSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProffesionJobTypeSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.ProffesionSync;
import com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper.TypeJobPositionSync;

import java.util.List;

public class MasterDataResponse extends BaseResponse {

    @SerializedName("IndustryTypeMaster")
    private List<IndustryTypeSync> industryTypeSyncList;

    @SerializedName("Kelurahan")
    private List<KelurahanSync> kelurahanSyncList;

    @SerializedName("Profession")
    private List<ProffesionSync> proffesionSyncList;

    @SerializedName("ProfJobPosition")
    private List<TypeJobPositionSync> typeJobPositionSyncList;

    @SerializedName("ProfJobType")
    private List<ProffesionJobTypeSync> proffesionJobTypeSyncList;

    @SerializedName("DataFinancial")
    private DataFinansialSync datafinancial;

    @SerializedName("BranchPrimary")
    private String branchPrimary;

    @SerializedName("AOBranch")
    private List<AoBranchMasterSync> aoBranch;

    public List<IndustryTypeSync> getIndustryTypeSyncList() {
        return industryTypeSyncList;
    }

    public void setIndustryTypeSyncList(List<IndustryTypeSync> industryTypeSyncList) {
        this.industryTypeSyncList = industryTypeSyncList;
    }

    public List<KelurahanSync> getKelurahanSyncList() {
        return kelurahanSyncList;
    }

    public void setKelurahanSyncList(List<KelurahanSync> kelurahanSyncList) {
        this.kelurahanSyncList = kelurahanSyncList;
    }

    public List<ProffesionSync> getProffesionSyncList() {
        return proffesionSyncList;
    }

    public void setProffesionSyncList(List<ProffesionSync> proffesionSyncList) {
        this.proffesionSyncList = proffesionSyncList;
    }

    public List<TypeJobPositionSync> getTypeJobPositionSyncList() {
        return typeJobPositionSyncList;
    }

    public void setTypeJobPositionSyncList(List<TypeJobPositionSync> typeJobPositionSyncList) {
        this.typeJobPositionSyncList = typeJobPositionSyncList;
    }

    public List<ProffesionJobTypeSync> getProffesionJobTypeSyncList() {
        return proffesionJobTypeSyncList;
    }

    public void setProffesionJobTypeSyncList(List<ProffesionJobTypeSync> proffesionJobTypeSyncList) {
        this.proffesionJobTypeSyncList = proffesionJobTypeSyncList;
    }

    public DataFinansialSync getDatafinancial() {
        return datafinancial;
    }

    public void setDatafinancial(DataFinansialSync datafinancial) {
        this.datafinancial = datafinancial;
    }

    public String getBranchPrimary() {
        return branchPrimary;
    }

    public void setBranchPrimary(String branchPrimary) {
        this.branchPrimary = branchPrimary;
    }

    public List<AoBranchMasterSync> getAoBranch() {
        return aoBranch;
    }

    public void setAoBranch(List<AoBranchMasterSync> aoBranch) {
        this.aoBranch = aoBranch;
    }
}
