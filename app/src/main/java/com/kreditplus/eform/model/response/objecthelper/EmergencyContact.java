package com.kreditplus.eform.model.response.objecthelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Iwan Nurdesa on 21/07/16.
 */
public class EmergencyContact {

    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Relationship")
    @Expose
    public String relationship;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("RT")
    @Expose
    public String rT;
    @SerializedName("RW")
    @Expose
    public String rW;
    @SerializedName("ProvinceCode")
    @Expose
    public String provinceCode;
    @SerializedName("CityCode")
    @Expose
    public String cityCode;
    @SerializedName("DistrictCode")
    @Expose
    public String districtCode;
    @SerializedName("VillageCode")
    @Expose
    public String villageCode;
    @SerializedName("ProvinceName")
    @Expose
    public String provinceName;
    @SerializedName("CityName")
    @Expose
    public String cityName;
    @SerializedName("DistrictName")
    @Expose
    public String districtName;
    @SerializedName("VillageName")
    @Expose
    public String villageName;
    @SerializedName("ZipCode")
    @Expose
    public String zipCode;
    @SerializedName("HomePhoneArea")
    @Expose
    public String homePhoneArea;
    @SerializedName("HomePhone")
    @Expose
    public String homePhone;
    @SerializedName("OfficePhone")
    @Expose
    public String officePhone;
    @SerializedName("OfficePhoneArea")
    @Expose
    public String officePhoneArea;
    @SerializedName("MobilePhone")
    @Expose
    public String mobilePhone;
    @SerializedName("City")
    @Expose
    public String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getrT() {
        return rT;
    }

    public void setrT(String rT) {
        this.rT = rT;
    }

    public String getrW() {
        return rW;
    }

    public void setrW(String rW) {
        this.rW = rW;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHomePhoneArea() {
        return homePhoneArea;
    }

    public void setHomePhoneArea(String homePhoneArea) {
        this.homePhoneArea = homePhoneArea;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficePhoneArea() {
        return officePhoneArea;
    }

    public void setOfficePhoneArea(String officePhoneArea) {
        this.officePhoneArea = officePhoneArea;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
