package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class Signature {

    @SerializedName("Applicant")
    @Expose
    public String applicant;
    @SerializedName("ApplicantHusbandWife")
    @Expose
    public String applicantHusbandWife;

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantHusbandWife() {
        return applicantHusbandWife;
    }

    public void setApplicantHusbandWife(String applicantHusbandWife) {
        this.applicantHusbandWife = applicantHusbandWife;
    }
}
