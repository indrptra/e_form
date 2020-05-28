package com.kreditplus.eform.model.response;

import com.google.gson.annotations.SerializedName;
import com.kreditplus.eform.model.response.objecthelper.KreditmuCity;
import com.kreditplus.eform.model.response.objecthelper.KreditmuEdu;
import com.kreditplus.eform.model.response.objecthelper.KreditmuHome;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobPosition;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobType;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKecamatan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKelurahan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuMarital;
import com.kreditplus.eform.model.response.objecthelper.KreditmuProfession;
import com.kreditplus.eform.model.response.objecthelper.KreditmuZipCode;

import java.util.List;

/**
 * Created by apc-lap012 on 18/07/17.
 */

public class KreditmuResponse {

    @SerializedName("Education")
    private List<KreditmuEdu> eduList;

    @SerializedName("MaritalStatus")
    private List<KreditmuMarital> maritalList;

    @SerializedName("HomeStatus")
    private List<KreditmuHome> homeList;

    @SerializedName("City")
    private List<KreditmuCity> cityList;

    @SerializedName("Kecamatan")
    private List<KreditmuKecamatan> kecamatanList;

    @SerializedName("Kelurahan")
    private List<KreditmuKelurahan> kelurahanlist;

    @SerializedName("ZipCode")
    private List<KreditmuZipCode> zipCode;

    @SerializedName("Profession")
    private List<KreditmuProfession> professionList;

    @SerializedName("JobType")
    private List<KreditmuJobType> jobTypeList;

    @SerializedName("JobPosition")
    private List<KreditmuJobPosition> jobPositionList;

    public List<KreditmuEdu> getEduList() {
        return eduList;
    }

    public void setEduList(List<KreditmuEdu> eduList) {
        this.eduList = eduList;
    }

    public List<KreditmuMarital> getMaritalList() {
        return maritalList;
    }

    public void setMaritalList(List<KreditmuMarital> maritalList) {
        this.maritalList = maritalList;
    }

    public List<KreditmuHome> getHomeList() {
        return homeList;
    }

    public void setHomeList(List<KreditmuHome> homeList) {
        this.homeList = homeList;
    }

    public List<KreditmuCity> getCityList() {
        return cityList;
    }

    public void setCityList(List<KreditmuCity> cityList) {
        this.cityList = cityList;
    }

    public List<KreditmuKecamatan> getKecamatanList() {
        return kecamatanList;
    }

    public void setKecamatanList(List<KreditmuKecamatan> kecamatanList) {
        this.kecamatanList = kecamatanList;
    }

    public List<KreditmuKelurahan> getKelurahanList() {
        return kelurahanlist;
    }

    public void setKelurahanList(List<KreditmuKelurahan> kelurahanList) {
        kelurahanlist = kelurahanList;
    }

    public List<KreditmuZipCode> getZipCode() {
        return zipCode;
    }

    public void setZipCode(List<KreditmuZipCode> zipCode) {
        this.zipCode = zipCode;
    }

    public List<KreditmuProfession> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<KreditmuProfession> professionList) {
        this.professionList = professionList;
    }

    public List<KreditmuJobType> getJobTypeList() {
        return jobTypeList;
    }

    public void setJobTypeList(List<KreditmuJobType> jobTypeList) {
        this.jobTypeList = jobTypeList;
    }

    public List<KreditmuJobPosition> getJobPositionList() {
        return jobPositionList;
    }

    public void setJobPositionList(List<KreditmuJobPosition> jobPositionList) {
        this.jobPositionList = jobPositionList;
    }
}
