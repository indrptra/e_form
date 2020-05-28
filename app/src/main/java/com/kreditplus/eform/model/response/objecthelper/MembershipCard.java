package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 13/10/16.
 */

public class MembershipCard {

    @SerializedName("IDMember")
    @Expose
    public String iDMember;
    @SerializedName("EffectiveDate")
    @Expose
    public String effectiveDate;

    public String getiDMember() {
        return iDMember;
    }

    public void setiDMember(String iDMember) {
        this.iDMember = iDMember;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    @SerializedName("ExpiredDate")
    @Expose
    public String expiredDate;


}
