package com.kreditplus.eform.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "MasterFormPengajuan")
public class MasterFormPengajuan {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "have_masking")
    private int HaveMasking;
    @DatabaseField(columnName = "application_id")
    private String applicationId;
    @DatabaseField(columnName = "app_id_backend")
    private String AppIdBackend;
    @DatabaseField(columnName = "blacklist_date")
    private String BlacklistDate;
    @DatabaseField(columnName = "uuid")
    private String Uuid;
    @DatabaseField(columnName = "customer_id_confins")
    private String CustomerIdConfins;
    @DatabaseField(columnName = "id_kpm")
    private String IdKpm;
    @DatabaseField(columnName = "branch")
    private String Branch;
    @DatabaseField(columnName = "master_branch")
    private String MasterBranch;
    @DatabaseField(columnName = "tipe_data_offering")
    private String TipeDataOffering;
    @DatabaseField(columnName = "tipe_pengajuan")
    private String TipePengajuan;
    @DatabaseField(columnName = "mobile_submission_key")
    private String MobileSubmissionKey;
    @DatabaseField(columnName = "ef_number")
    private String EfNumber;
    @DatabaseField(columnName = "tipe_save_data")
    private String TipeSaveData;
    @DatabaseField(columnName = "status_sync")
    private int StatusSync;
    @DatabaseField(columnName = "created_at")
    private String CreatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHaveMasking() {
        return HaveMasking;
    }

    public void setHaveMasking(int haveMasking) {
        HaveMasking = haveMasking;
    }

    public String getBlacklistDate() {
        return BlacklistDate;
    }

    public void setBlacklistDate(String blacklistDate) {
        BlacklistDate = blacklistDate;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public String getCustomerIdConfins() {
        return CustomerIdConfins;
    }

    public void setCustomerIdConfins(String customerIdConfins) {
        CustomerIdConfins = customerIdConfins;
    }

    public String getIdKpm() {
        return IdKpm;
    }

    public void setIdKpm(String idKpm) {
        IdKpm = idKpm;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getMasterBranch() {
        return MasterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        MasterBranch = masterBranch;
    }

    public String getTipeDataOffering() {
        return TipeDataOffering;
    }

    public void setTipeDataOffering(String tipeDataOffering) {
        TipeDataOffering = tipeDataOffering;
    }

    public String getTipePengajuan() {
        return TipePengajuan;
    }

    public void setTipePengajuan(String tipePengajuan) {
        TipePengajuan = tipePengajuan;
    }

    public String getMobileSubmissionKey() {
        return MobileSubmissionKey;
    }

    public void setMobileSubmissionKey(String mobileSubmissionKey) {
        MobileSubmissionKey = mobileSubmissionKey;
    }

    public String getEfNumber() {
        return EfNumber;
    }

    public void setEfNumber(String efNumber) {
        EfNumber = efNumber;
    }

    public String getTipeSaveData() {
        return TipeSaveData;
    }

    public void setTipeSaveData(String tipeSaveData) {
        TipeSaveData = tipeSaveData;
    }

    public int getStatusSync() {
        return StatusSync;
    }

    public void setStatusSync(int statusSync) {
        StatusSync = statusSync;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAppIdBackend() {
        return AppIdBackend;
    }

    public void setAppIdBackend(String appIdBackend) {
        AppIdBackend = appIdBackend;
    }
}
