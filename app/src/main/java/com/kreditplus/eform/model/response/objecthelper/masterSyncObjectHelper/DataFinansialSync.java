package com.kreditplus.eform.model.response.objecthelper.masterSyncObjectHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apc-lap012 on 22/02/17.
 */

public class DataFinansialSync {

    @SerializedName("product_insurance_persen")
    private String productInsurancePersen;
    @SerializedName("product_insurance_nominal")
    private int productInsuranceNominal;
    @SerializedName("people_insurance_persen")
    private String peopleInsurancePersen;
    @SerializedName("people_insurance_nominal")
    private int peopleInsuranceNominal;
    @SerializedName("total_insurance_persen")
    private String totalInsurancePersen;
    @SerializedName("total_insurance_nominal")
    private String totalInsuranceNominal;
    @SerializedName("AdminFeeLainya")
    private String adminFeeLainnya;

    public String getProductInsurancePersen() {
        return productInsurancePersen;
    }

    public void setProductInsurancePersen(String productInsurancePersen) {
        this.productInsurancePersen = productInsurancePersen;
    }

    public int getProductInsuranceNominal() {
        return productInsuranceNominal;
    }

    public void setProductInsuranceNominal(int productInsuranceNominal) {
        this.productInsuranceNominal = productInsuranceNominal;
    }

    public String getPeopleInsurancePersen() {
        return peopleInsurancePersen;
    }

    public void setPeopleInsurancePersen(String peopleInsurancePersen) {
        this.peopleInsurancePersen = peopleInsurancePersen;
    }

    public int getPeopleInsuranceNominal() {
        return peopleInsuranceNominal;
    }

    public void setPeopleInsuranceNominal(int peopleInsuranceNominal) {
        this.peopleInsuranceNominal = peopleInsuranceNominal;
    }

    public String getTotalInsurancePersen() {
        return totalInsurancePersen;
    }

    public void setTotalInsurancePersen(String totalInsurancePersen) {
        this.totalInsurancePersen = totalInsurancePersen;
    }

    public String getTotalInsuranceNominal() {
        return totalInsuranceNominal;
    }

    public void setTotalInsuranceNominal(String totalInsuranceNominal) {
        this.totalInsuranceNominal = totalInsuranceNominal;
    }

    public String getAdminFeeLainnya() {
        return adminFeeLainnya;
    }

    public void setAdminFeeLainnya(String adminFeeLainnya) {
        this.adminFeeLainnya = adminFeeLainnya;
    }

    public String toString(){return adminFeeLainnya;}
}
