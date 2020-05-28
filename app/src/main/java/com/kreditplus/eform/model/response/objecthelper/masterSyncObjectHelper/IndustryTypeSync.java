package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 17/02/17.
 */

public class IndustryTypeSync {

   @SerializedName("IndustryTypeID")
    private String idIndustryType;

   @SerializedName("Description")
    private String Descrption;

   @SerializedName("IsActive")
    private String isActive;

    public String getIdIndustryType() {
        return idIndustryType;
    }

    public void setIdIndustryType(String idIndustryType) {
        this.idIndustryType = idIndustryType;
    }

    public String getDescrption() {
        return Descrption;
    }

    public void setDescrption(String descrption) {
        Descrption = descrption;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
