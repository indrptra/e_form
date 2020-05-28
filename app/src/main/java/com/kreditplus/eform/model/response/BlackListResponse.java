package com.kreditplus.eform.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.ApplicationBlacklist;
import com.kreditplus.eform.model.response.objecthelper.ApplicationType;

import java.util.List;

/**
 * Created by Iwan Nurdesa on 04/10/16.
 */

public class BlackListResponse extends BaseResponse {

    @SerializedName("is_blacklist")
    private int isBlackList;
    @SerializedName("is_empty")
    private int isEmptyData;
    @SerializedName("mobile_submission_key")
    private String mobileSubmissionKey;
    @SerializedName("application_type")
    private List<ApplicationType> applicationType;
    @SerializedName("status_kreditmu")
    private String statusKreditmu;
    @SerializedName("product_insurance_persen")
    private Double productInsurancePersen;
    @SerializedName("product_insurance_nominal")
    private Integer productInsuranceNominal;
    @SerializedName("people_insurance_persen")
    private Double peopleInsurancePersen;
    @SerializedName("people_insurance_nominal")
    private Integer peopleInsuranceNominal;
    @SerializedName("total_insurance_persen")
    private Double totalInsurancePersen;
    @SerializedName("total_insurance_nominal")
    private Integer totalInsuranceNominal;
    @SerializedName("Application")
    private List<ApplicationBlacklist> applicationBlacklists;
    @SerializedName("adminFeeLainnya")
    private Integer feeAdminlain;
    @SerializedName("dateStart")
    private String dateStart;
    @SerializedName("FullName")
    private List<String> fullNames;
    @SerializedName("BucketMessage")
    private String bucketMessage;
    @SerializedName("is_new")
    private int isNew;
    @SerializedName("no_agreement")
    private String noAgreement;
    @SerializedName("time_delay")
    private String timeDelay;
    @SerializedName("amount_of_fines")
    private String amountOfFines;
    @SerializedName("AOSalesStatus")
    private String AOSalesStatus;

    public List<ApplicationType> getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(List<ApplicationType> applicationType) {
        this.applicationType = applicationType;
    }

    public String getStatusKreditmu() {
        return statusKreditmu;
    }

    public void setStatusKreditmu(String statusKreditmu) {
        this.statusKreditmu = statusKreditmu;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsBlackList() {
        return isBlackList;
    }

    public void setIsBlackList(int isBlackList) {
        this.isBlackList = isBlackList;
    }

    public String getMobileSubmissionKey() {
        return mobileSubmissionKey;
    }

    public void setMobileSubmissionKey(String mobileSubmissionKey) {
        this.mobileSubmissionKey = mobileSubmissionKey;
    }

    public Double getProductInsurancePersen() {
        return productInsurancePersen;
    }

    public void setProductInsurancePersen(Double productInsurancePersen) {
        this.productInsurancePersen = productInsurancePersen;
    }

    public Integer getProductInsuranceNominal() {
        return productInsuranceNominal;
    }

    public void setProductInsuranceNominal(Integer productInsuranceNominal) {
        this.productInsuranceNominal = productInsuranceNominal;
    }

    public Double getPeopleInsurancePersen() {
        return peopleInsurancePersen;
    }

    public void setPeopleInsurancePersen(Double peopleInsurancePersen) {
        this.peopleInsurancePersen = peopleInsurancePersen;
    }

    public Integer getPeopleInsuranceNominal() {
        return peopleInsuranceNominal;
    }

    public void setPeopleInsuranceNominal(Integer peopleInsuranceNominal) {
        this.peopleInsuranceNominal = peopleInsuranceNominal;
    }

    public Double getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(Double totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public Integer getTotalInsuranceNominal() {
        return totalInsuranceNominal;
    }

    public void setTotalInsuranceNominal(Integer totalInsuranceNominal) {
        this.totalInsuranceNominal = totalInsuranceNominal;
    }

    public List<ApplicationBlacklist> getApplicationBlacklists() {
        return applicationBlacklists;
    }

    public void setApplicationBlacklists(List<ApplicationBlacklist> applicationBlacklists) {
        this.applicationBlacklists = applicationBlacklists;
    }

    public int getIsEmptyData() {
        return isEmptyData;
    }

    public void setIsEmptyData(int isEmptyData) {
        this.isEmptyData = isEmptyData;
    }

    public Integer getFeeAdminlain() {
        return feeAdminlain;
    }

    public void setFeeAdminlain(Integer feeAdminlain) {
        this.feeAdminlain = feeAdminlain;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public List<String> getFullNames() {
        return fullNames;
    }

    public void setFullNames(List<String> fullNames) {
        this.fullNames = fullNames;
    }

    public String getBucketMessage() {
        return bucketMessage;
    }

    public void setBucketMessage(String bucketMessage) {
        this.bucketMessage = bucketMessage;
    }

    public String getNoAgreement() {
        return noAgreement;
    }

    public void setNoAgreement(String noAgreement) {
        this.noAgreement = noAgreement;
    }

    public String getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(String timeDelay) {
        this.timeDelay = timeDelay;
    }

    public String getAmountOfFines() {
        return amountOfFines;
    }

    public void setAmountOfFines(String amountOfFines) {
        this.amountOfFines = amountOfFines;
    }

    public String getAOSalesStatus() {
        return AOSalesStatus;
    }

    public void setAOSalesStatus(String AOSalesStatus) {
        this.AOSalesStatus = AOSalesStatus;
    }
}
